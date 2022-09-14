package com.vsii.listener;

import com.filenet.api.core.Folder;
import com.google.gson.Gson;
import com.vsii.entity.*;
import com.vsii.enums.SourceEnum;
import com.vsii.model.*;
import com.vsii.utils.DateUtils;
import com.vsii.utils.FilenetUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScanServiceNCListener extends BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebserviceNCListener.class);

    @KafkaListener(topics = "${spring.kafka.topic.name.scan-new}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws Exception {
        String source = "Manual";
        LOGGER.info(String.format("Message received -> %s", message));
        String scanBackup = fileService.buildFolderPath(propertiesConfig.getFolderBackup(), propertiesConfig.getFolderScanNCPPX());
        String scanError = fileService.buildFolderPath(propertiesConfig.getFolderError(), propertiesConfig.getFolderScanNCPPX());
        File folder = new File(message);
        File[] files = folder.listFiles();
        Map<String, Form> mapForm = formRepository.findByFormActive("Y").stream().collect(Collectors.toMap(Form::getFormId, form -> form));
        List<ErrorDetails> lstError = new ArrayList<>();
        String[] lstExtensions = propertiesConfig.getFileExtensions();
        String fileDelimiter = propertiesConfig.getFileDelimiter();
        List<File> fileNotFileJson = new ArrayList<>();
        for (File file : files) {
            if (!file.getName().equals("PPX.json")) {
                fileNotFileJson.add(file);
                ErrorDetails error = validateService.validateFile(file, lstExtensions, fileDelimiter, mapForm);
                if (error == null) {
                    LOGGER.info("File: " + file.getAbsolutePath() + " match. Add to list Attachments");
                    FileModel model = new FileModel();
                    model.setFilePath(file.getAbsolutePath());
                    String[] fileParams = file.getName().split(fileDelimiter, -1);
                    model.setFormId(fileParams[0]);
                    model.setDisplayName(mapForm.get(fileParams[0]).getFormName());
                } else {
                    LOGGER.info("File: " + file.getAbsolutePath() + " not match. Add to list Error");
                    lstError.add(error);
                }
            }
        }
        if (lstError.size() > 0) {
            LOGGER.info("Folder " + message + " error file. Move all folder to error!!!!!");
            // log Detail
            LogDetailModel logMoveFolder = new LogDetailModel(message);
            logMoveFolder.setProcessName("MoveFolder");
            logMoveFolder.setStartTime(DateUtils.format(new Date(), LOG_DATE_FORMAT));
            // end log Detail
            File fileError = new File(fileService.buildFolderPath(scanError, folder.getName()));
            fileService.moveFolder(folder, fileError);
            ErrorModel errorModel = new ErrorModel();
            errorModel.setErrorDetails(lstError);
            fileService.buildErrorFile(fileService.buildFolderPath(scanError, folder.getName()), errorModel);
        }
        if (lstError.size() <= 0) {
            String[] properties = folder.getName().split("_", -1);
            ClaimModel claimModel = new ClaimModel();
            String[] claimRequestId = properties[0].split("-", -1);
            claimModel.setClaimRequestId(claimRequestId[0]);
            claimModel.setClaimId(properties[0]);
            claimModel.setSource("PPX");
            claimModel.setBenCode(properties[1]);
            ErrorModel errorModel = new ErrorModel();
            ErrorDetails errorDetails = new ErrorDetails();
            String pathJson = folder.getPath() + "/" + "PPX.json";
            String json = getStringJson(pathJson);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            Folder f = fileService.findByAppNo(FilenetUtils.getObjectStore(propertiesConfig), claimModel.getClaimId());
            Boolean checkInsertHistory = false;
            if (f == null) {
                try {
                    Folder folCreate = fileService.createFolder(FilenetUtils.getObjectStore(propertiesConfig), jsonObject, claimModel, source, propertiesConfig.getPathSaveFolder());
                    for (File file : fileNotFileJson) {
                        fileService.createDocument(folCreate, file, FilenetUtils.getObjectStore(propertiesConfig), jsonObject, claimModel);
                    }
                    fileService.createWorkFlow(folCreate, vwSession, claimModel, jsonObject);
                    File fileBackup = new File(fileService.buildFolderPath(scanBackup, folder.getName()));
                    fileService.moveFolder(folder, fileBackup);
// Save database
                    saveDatabase(jsonObject, claimModel);
                    checkInsertHistory = true;
                } catch (Exception e) {
                    errorDetails.setErrorStackTrace(e.getLocalizedMessage());
                    errorDetails.setErrorRootCause(e.getMessage());
                    List<ErrorDetails> errorDetailsList = new ArrayList<>();
                    errorDetailsList.add(errorDetails);
                    errorModel.setErrorDetails(errorDetailsList);
                    LOGGER.error(e.getMessage());
                    File fileError = new File(fileService.buildFolderPath(scanError, folder.getName()));
                    fileService.moveFolder(folder, fileError);
                    fileService.buildErrorFile(fileService.buildFolderPath(scanError, folder.getName()), errorModel);
                }
            } else {
                try {
                    for (File file : fileNotFileJson) {
                        fileService.createDocument(f, file, FilenetUtils.getObjectStore(propertiesConfig), jsonObject, claimModel);
                    }
                    File fileBackup = new File(fileService.buildFolderPath(scanBackup, folder.getName()));
                    fileService.moveFolder(folder, fileBackup);

                } catch (Exception e) {
                    errorDetails.setErrorStackTrace(e.getLocalizedMessage());
                    errorDetails.setErrorRootCause(e.getMessage());
                    List<ErrorDetails> errorDetailsList = new ArrayList<>();
                    errorDetailsList.add(errorDetails);
                    errorModel.setErrorDetails(errorDetailsList);
                    LOGGER.error(e.getMessage());
                    File fileError = new File(fileService.buildFolderPath(scanError, folder.getName()));
                    fileService.moveFolder(folder, fileError);
                    fileService.buildErrorFile(fileService.buildFolderPath(scanError, folder.getName()), errorModel);
                }
            }

            if (checkInsertHistory) {
                List<IwsCaseHistoryEntity> lst = new ArrayList<>();
                IwsCaseHistoryEntity entity = new IwsCaseHistoryEntity();
                Date currentDate = new Date();
                Timestamp timestamp = new Timestamp(currentDate.getTime());
                entity.setAppNo(claimModel.getClaimId());
                entity.setCaseStatus("Completed Normal");
                entity.setResponse("New Claim");
                entity.setCreatedDate(timestamp);
                entity.setCompletedTime(timestamp);
                entity.setReceivedTime(timestamp);
                entity.setSubmissionDate(timestamp);
                entity.setUserId(filenetConfig.getWfUserName());
                lst.add(entity);
                IwsCaseHistoryEntity saleEntity = new IwsCaseHistoryEntity();
                Timestamp submissionDate = DateUtils.convertTimestamp(jsonObject.getSubmissionTimestamp());
                saleEntity.setAppNo(claimModel.getClaimId());
                saleEntity.setCaseStatus("Completed Normal");
                saleEntity.setCreatedDate(timestamp);
                saleEntity.setCompletedTime(submissionDate);
                saleEntity.setReceivedTime(submissionDate);
                saleEntity.setSubmissionDate(submissionDate);
                if (source.equals(SourceEnum.Manual.toString())) {
                    saleEntity.setResponse("Scan Submitted");
                    saleEntity.setUserId("Scan");
                } else {
                    saleEntity.setResponse("Sales Submmited");
                    saleEntity.setUserId("Sales");
                }
                lst.add(saleEntity);
                iwsCaseHistoryService.save(lst);
            }
        }
    }

    private void saveDatabase(JsonObject jsonObject, ClaimModel claimModel) {
        try {
            LOGGER.info("Start Save database Scan service NC");
            Integer claimRequestEntity = null;
            claimRequestService.save(jsonObject);
            if (claimRequestEntity != null) {
                claimBenefitInfoService.Save(jsonObject, claimRequestEntity);
                claimCaseService.Save(jsonObject, claimRequestEntity, claimModel);
            }
            List<Integer> idClaimBenefit = claimBenefitService.Save(jsonObject);
            if (idClaimBenefit != null) {
                for (Integer id : idClaimBenefit) {
                    claimBenefitAttService.Save(jsonObject, id);
                }
            }
        } catch (Exception e) {
            LOGGER.info("Save database Scan service NC false: ");
            e.printStackTrace();
        }
    }

    public static String getStringJson(String path) {
        String json = "";
        try {
            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject data = (JSONObject) parser.parse(
                    new FileReader(path));//path to the JSON file.

            return data.toJSONString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return json;
        }
    }
}
