package vn.systems.claim.services.capture.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.claim.capture.dto.ClaimBenefitDTO;
import vn.claim.capture.dto.JsonObjectDTO;
import vn.claim.capture.model.ClaimBenefit;
import vn.claim.capture.model.JsonObject;
import vn.systems.claim.services.capture.Config.Config;
import vn.systems.claim.services.capture.entity.ClaimSequence;
import vn.systems.claim.services.capture.repository.ClaimSequenceRepository;
import vn.systems.claim.services.capture.service.ClaimService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class ClaimServiceImpl implements ClaimService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimServiceImpl.class);

    @Autowired
    private Config config;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ClaimSequenceRepository claimSequenceRepository;

    public boolean createFolderParentToServer(JsonObject jsonObject) {
        boolean createFile = true;
        try {
            LOGGER.info("Start Created parent folder and file attachment with source: " + jsonObject.getSource());
            List<ClaimBenefit> claimBenefits = jsonObject.getLifeAssured().getClaimBenefits();
            String claimRequestId = jsonObject.getClaimRequestId();
            if (claimBenefits.size() > 0) {
                LOGGER.info("Check file attachment in jsonObject: " + claimBenefits.size());
                for (int i = 0; i < claimBenefits.size(); i++) {
                    try {
                        LOGGER.info("Created Folder Parent and file attachment...");
                        createFile = createFileAttachment(claimBenefits.get(i), claimRequestId, jsonObject, i);
                        if (createFile == false) {
                            break;
                        }

                    } catch (Exception e) {
                        createFile = false;
                        LOGGER.info("Error Created parent folder exception : " + e.getMessage());
                    }
                }
            }
            LOGGER.info("Save file successfully");
            return createFile;
        } catch (Exception e) {
            LOGGER.error("Error create folder: " + e.getMessage());
            return createFile;
        }
    }


    @Override
    public boolean createFileAttachment(ClaimBenefit claimBen, String claimRequestId, JsonObject jsonObject, int count) throws IOException {
        LOGGER.info("Create file Attachment in folder");
        FileOutputStream fileOutputStream = null;
        boolean createFile = true;
        String formatValue = "";
        String fName = "";
        List<ClaimSequence> sequences = claimSequenceRepository.getClaimSequence(claimRequestId);
        if (sequences.size() > 0) {
            ClaimSequence claimSequence = sequences.get(0);
            int temp = claimSequence.getCurrentClaimId();
            formatValue = String.format("%03d", temp + 1);
            fName = claimRequestId + "-" + formatValue;
            claimSequence.setClaimRequestId(claimRequestId);
            claimSequence.setCurrentClaimId(temp + 1);
            claimSequenceRepository.save(claimSequence);
        } else {
            ClaimSequence claimSequence = new ClaimSequence();
            int temp = 1;
            formatValue = String.format("%03d", temp);
            fName = claimRequestId + "-" + formatValue;
            claimSequence.setClaimRequestId(claimRequestId);
            claimSequence.setCurrentClaimId(temp);
            claimSequenceRepository.save(claimSequence);
        }
        try {
            String benCode = claimBen.getBenCode();
            ModelMapper modelMapper = new ModelMapper();
            JsonObjectDTO jsonObjectDTO = modelMapper.map(jsonObject, JsonObjectDTO.class);
            for (int i = 0; i < jsonObjectDTO.getLifeAssured().getClaimBenefits().size(); i++) {
                if (i == count) {
                    jsonObjectDTO.getLifeAssured().getClaimBenefits().get(i).setClaimId(fName);
                } else {
                    jsonObjectDTO.getLifeAssured().getClaimBenefits().get(i).setClaimId("");
                }
            }
            for (int i = 0; i < claimBen.getAttachments().size(); i++) {
                StringBuilder folderName = new StringBuilder();
                UUID uuid = UUID.randomUUID();
                folderName.append(fName).append("_").append(benCode).append("_").append(uuid);
                String newFolderDir = config.getFolderParentServer();
                LOGGER.info("newFolderDir: " + newFolderDir);
                String pathFolderName = newFolderDir + folderName;
                File folderParent = new File(pathFolderName);
                if (!folderParent.exists()) {
                    folderParent.mkdir();
                }
                CreateFileJsonLocal(jsonObjectDTO, pathFolderName, fName);
                String nameFileAttach = claimBen.getAttachments().get(i).getFormId() + "_" + claimBen.getAttachments().get(i).getName();
                String pathFile = pathFolderName + "/" + nameFileAttach;
                String data = claimBen.getAttachments().get(i).getData();
                byte[] bytes = Base64.decodeBase64(data);
                File file = new File(pathFile);
                if (!file.exists()) {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            createFile = false;
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            LOGGER.info("Error Create file Attachment in folder: " + e.getMessage());
        }
        return createFile;
    }


    @Override
    public void createFolderParentToLocal(JsonObject jsonObject) {
        LOGGER.info("Start Created parent folder and file attachment with source: " + jsonObject.getSource());
        List<ClaimBenefit> claimBenefits = jsonObject.getLifeAssured().getClaimBenefits();
        String claimRequestId = jsonObject.getClaimRequestId();
        if (claimBenefits.size() > 0) {
            LOGGER.info("Check file attachment in jsonObject: " + claimBenefits.size());
            for (int i = 0; i < claimBenefits.size(); i++) {
                try {
                    LOGGER.info("Created Folder Parent and file attachment...");
                    createFileAttachmentLocal(claimBenefits.get(i), claimRequestId, jsonObject, i);
                } catch (Exception e) {

                    LOGGER.info("Error Created parent folder exception : " + e.getMessage());
                }
            }
        }
        LOGGER.info("Save file successfully");
    }

    @Override
    public void CreateFileJsonLocal(JsonObjectDTO jsonObjectDTO, String path, String claimId) {
        LOGGER.info("Create Json File in Folder Parent");
        try {
            FileWriter file = new FileWriter(path + "/" + jsonObjectDTO.getSource() + ".json", StandardCharsets.UTF_8);
            LOGGER.info("path: " + path);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            file.write(ow.writeValueAsString(jsonObjectDTO));
            file.close();
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
        }
    }

    @Override
    public void createFileAttachmentLocal(ClaimBenefit claimBenefit, String claimRequestId, JsonObject jsonObject, int count) throws IOException {
        LOGGER.info("Create file Attachment in folder");
        FileOutputStream fileOutputStream = null;
        String formatValue = "";
        String fName = "";
        List<ClaimSequence> sequences = claimSequenceRepository.getClaimSequence(claimRequestId);
        if (sequences.size() > 0) {
            ClaimSequence claimSequence = sequences.get(0);
            int temp = claimSequence.getCurrentClaimId();
            formatValue = String.format("%03d", temp + 1);
            fName = claimRequestId + "-" + formatValue;
            claimSequence.setClaimRequestId(claimRequestId);
            claimSequence.setCurrentClaimId(temp + 1);
            claimSequenceRepository.save(claimSequence);
        } else {
            ClaimSequence claimSequence = new ClaimSequence();
            int temp = 1;
            formatValue = String.format("%03d", temp);
            fName = claimRequestId + "-" + formatValue;
            claimSequence.setClaimRequestId(claimRequestId);
            claimSequence.setCurrentClaimId(temp);
            claimSequenceRepository.save(claimSequence);
        }
        try {
            String benCode = claimBenefit.getBenCode();
            ModelMapper modelMapper = new ModelMapper();
            JsonObjectDTO jsonObjectDTO = modelMapper.map(jsonObject, JsonObjectDTO.class);
            for (int i = 0; i < jsonObjectDTO.getLifeAssured().getClaimBenefits().size(); i++) {
                if (i == count) {
                    jsonObjectDTO.getLifeAssured().getClaimBenefits().get(i).setClaimId(fName);
                } else {
                    jsonObjectDTO.getLifeAssured().getClaimBenefits().get(i).setClaimId("");
                }
            }
            for (int i = 0; i < claimBenefit.getAttachments().size(); i++) {
                StringBuilder folderName = new StringBuilder();
                UUID uuid = UUID.randomUUID();
                folderName.append(fName).append("_").append(benCode).append("_").append(uuid);
                String newFolderDir = config.getFolderParentLocal();
                LOGGER.info("newFolderDir: " + newFolderDir);
                String pathFolderName = newFolderDir + folderName;
                File folderParent = new File(pathFolderName);
                if (!folderParent.exists()) {
                    folderParent.mkdir();
                }
                CreateFileJsonLocal(jsonObjectDTO, pathFolderName, fName);
                String nameFileAttach = claimBenefit.getAttachments().get(i).getFormId() + "_" + claimBenefit.getAttachments().get(i).getName();
                String pathFile = pathFolderName + "/" + nameFileAttach;
                String data = claimBenefit.getAttachments().get(i).getData();
                byte[] bytes = Base64.decodeBase64(data);
                File file = new File(pathFile);
                if (!file.exists()) {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            LOGGER.info("Error Create file Attachment in folder: " + e.getMessage());
        }
    }

}
