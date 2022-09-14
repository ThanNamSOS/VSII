package com.vsii.listener;

import com.filenet.api.core.Folder;
import com.google.gson.Gson;
import com.vsii.entity.*;
import com.vsii.enums.ErrorEnum;
import com.vsii.model.*;
import com.vsii.repository.ClaimFollowupRepository;
import com.vsii.utils.DateUtils;
import com.vsii.utils.FilenetUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WebServiceAddListener extends BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceAddListener.class);
    @Autowired
    private ClaimFollowupRepository claimFollowupRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name.add}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws Exception {
        LOGGER.info(String.format("Message received -> %s", message));
        String webServiceBackup = fileService.buildFolderPath(propertiesConfig.getFolderBackup(), propertiesConfig.getFolderWebserviceADD());
        String webServiceError = fileService.buildFolderPath(propertiesConfig.getFolderError(), propertiesConfig.getFolderWebserviceADD());
        File folder = new File(message);
        String[] inputParams = folder.getName().split("_", -1);
        List<ErrorDetails> lstError = new ArrayList<>();
//        if (inputParams[1] != null) {
//            List<ClaimFollowup> claimFollowups = claimFollowupRepository.findAllByFollowupCode(inputParams[1]);
//            if (claimFollowups.size() <= 0) {
//                ErrorDetails error = new ErrorDetails();
//                error.setErrorRootCause(folder.getName());
//                error.setErrorStackTrace(ErrorEnum.INVALID_FOLLOW_UP.getErrorDesc());
//                lstError.add(error);
//            }
//        }
        File[] files = folder.listFiles();
        Map<String, Form> mapForm = formRepository.findByFormActiveAndFormClassname("Y","ClaimDocument").stream().collect(Collectors.toMap(Form::getFormId, form -> form));

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
            File fileError = new File(fileService.buildFolderPath(webServiceError, folder.getName()));
            ErrorModel errorModel = new ErrorModel();
            errorModel.setErrorDetails(lstError);
            fileService.moveFolder(folder, fileError);
            fileService.buildErrorFile(fileService.buildFolderPath(webServiceError, folder.getName()), errorModel);

        }
        ErrorModel errorModel = new ErrorModel();
        ErrorDetails errorDetails = new ErrorDetails();
        if (lstError.size() <= 0) {
            try {
                String[] properties = folder.getName().split("_", -1);
                ClaimModel claimModel = new ClaimModel();
                String[] claimRequestId = properties[0].split("-", -1);
                claimModel.setClaimRequestId(claimRequestId[0]);
                claimModel.setClaimId(properties[0]);
                claimModel.setSource("PPX");
                claimModel.setBenCode(properties[1]);
                String pathJson = folder.getPath() + "/" + "PPX.json";
                String json = getStringJson(pathJson);
                Gson gson = new Gson();
                JsonObjectFollowup jsonObjectFollowup = gson.fromJson(json, JsonObjectFollowup.class);
                Folder f = fileService.findByAppNo(FilenetUtils.getObjectStore(propertiesConfig), claimModel.getClaimId());
                if (f != null) {
                    try {
                        for (File file : fileNotFileJson) {
                            fileService.createDocumentAdd(f, file, FilenetUtils.getObjectStore(propertiesConfig), jsonObjectFollowup, claimModel);
                        }
                        saveDatabase(jsonObjectFollowup,claimModel);

                        File fileBackup = new File(fileService.buildFolderPath(webServiceBackup, folder.getName()));
                        fileService.moveFolder(folder, fileBackup);
// SAVE DATABASE

                    } catch (Exception e) {
                        errorDetails.setErrorStackTrace(e.getLocalizedMessage());
                        errorDetails.setErrorRootCause(e.getMessage());
                        List<ErrorDetails> errorDetailsList = new ArrayList<>();
                        errorDetailsList.add(errorDetails);
                        errorModel.setErrorDetails(errorDetailsList);
                        LOGGER.error(e.getMessage());
                        File fileError = new File(fileService.buildFolderPath(webServiceError, folder.getName()));
                        fileService.moveFolder(folder, fileError);
                        fileService.buildErrorFile(fileService.buildFolderPath(webServiceError, folder.getName()), errorModel);
                    }
                }
                if (f == null) {
                    ErrorDetails error = new ErrorDetails();
                    List<ErrorDetails> errorDetailsList = new ArrayList<>();
                    errorDetailsList.add(error);
                    error.setErrorRootCause(folder.getName());
                    error.setErrorStackTrace(ErrorEnum.CLAIM_ID.getErrorDesc());
                    errorModel.setErrorDetails(errorDetailsList);
                    File fileError = new File(fileService.buildFolderPath(webServiceError, folder.getName()));
                    fileService.moveFolder(folder, fileError);
                    fileService.buildErrorFile(fileService.buildFolderPath(webServiceError, folder.getName()), errorModel);
                }
            } catch (Exception e) {
                errorDetails.setErrorStackTrace(e.getLocalizedMessage());
                errorDetails.setErrorRootCause(e.getMessage());
                List<ErrorDetails> errorDetailsList = new ArrayList<>();
                errorDetailsList.add(errorDetails);
                errorModel.setErrorDetails(errorDetailsList);
                LOGGER.error(e.getMessage());
                File fileError = new File(fileService.buildFolderPath(webServiceError, folder.getName()));
                fileService.moveFolder(folder, fileError);
                fileService.buildErrorFile(fileService.buildFolderPath(webServiceError, folder.getName()), errorModel);
            }
        }
    }
    private void saveDatabase(JsonObjectFollowup objectFollowup, ClaimModel claimModel) {
        LOGGER.info("Start Save database");
        ClaimAdditionalEntity claimAdditional = claimAdditionalService.Save(objectFollowup, claimModel);
        if (claimAdditional != null) {
            List<Integer> idAddBens = additionalBenefitService.Save(objectFollowup, claimAdditional.getId());
            if (idAddBens != null) {
                for ( Integer idAddBen: idAddBens) {
                    claimAdditionalBenefitAttService.Save(objectFollowup, idAddBen);
                }
            }
            LOGGER.info("End Save database");
        }
    }
    public static String getStringJson(String path) {
        String json = "";
        try {
            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject data = (JSONObject) parser.parse(new FileReader(path));//path to the JSON file.

            return data.toJSONString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return json;
        }
    }
}
