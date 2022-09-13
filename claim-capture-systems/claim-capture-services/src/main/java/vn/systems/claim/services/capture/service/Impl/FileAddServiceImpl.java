package vn.systems.claim.services.capture.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.claim.capture.model.*;
import vn.systems.claim.services.capture.Config.Config;
import vn.systems.claim.services.capture.service.FileAddService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class FileAddServiceImpl implements FileAddService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileAddServiceImpl.class);
    @Autowired
    private Config config;

    @Autowired
    private FileServiceImpl fileService;

    @Override
    public boolean createFolderParentToServer(JsonObjectFollowup jsonObjectFollowup) {
        boolean createFile = true;
        try {
            LOGGER.info("Start Created parent folder server and file attachment with claimRequesId: " + jsonObjectFollowup.getClaimRequestId());
            String claimRequestId = jsonObjectFollowup.getClaimRequestId();
            try {
                LOGGER.info("Created Folder Parent and file attachment...");
                createFile = createFileAttachment(claimRequestId, jsonObjectFollowup);
            } catch (Exception e) {
                createFile = false;
                LOGGER.info("Error Created parent folder exception : " + e.getMessage());
            }
            LOGGER.info("Save file successfully");
            return createFile;
        } catch (Exception e) {
            LOGGER.error("Error create folder: " + e.getMessage());
            return createFile;
        }
    }

    @Override
    public boolean createFileAttachment(String claimRequestId, JsonObjectFollowup jsonObjectFollowup) throws IOException {
        LOGGER.info("Create file Attachment in folder server");
        FileOutputStream fileOutputStream = null;
        boolean createFile = true;
        try {
            for (Attachments a : jsonObjectFollowup.getAttachments()) {
                String followUpCode = a.getFollowupCode();
                StringBuilder folderName = new StringBuilder();
                UUID uuid = UUID.randomUUID();
               // folderName.append(jsonObjectFollowup.getClaimId()).append("_").append(followUpCode).append("_").append(uuid);
                folderName.append(jsonObjectFollowup.getClaimId()).append("_").append(uuid);
                String newFolderDir = config.getFolderParentAddServer();
                LOGGER.info("newFolderDir: " + newFolderDir);
                String pathFolderName = newFolderDir + folderName;
                File folderParent = new File(pathFolderName);
                if (!folderParent.exists()) {
                    folderParent.mkdir();
                }
                CreateFileJsonLocal(jsonObjectFollowup, pathFolderName);
                String nameFileAttach = a.getFormId() + "_" + a.getName();
                String pathFile = pathFolderName + "/" + nameFileAttach;
                String data = a.getData();
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
            LOGGER.info("Error Create file Attachment in folder server: " + e.getMessage());
        }
        return createFile;
    }

    @Override
    public void createFolderParentToLocal(JsonObjectFollowup jsonObjectFollowup) {
        LOGGER.info("Start Created parent folder local and file attachment with claimRequesId: " + jsonObjectFollowup.getClaimRequestId());
        String claimRequestId = jsonObjectFollowup.getClaimRequestId();
        try {
            LOGGER.info("Created Folder Parent local and file attachment...");
            createFileAttachmentLocal(claimRequestId, jsonObjectFollowup);
        } catch (Exception e) {

            LOGGER.info("Error Created parent folder local exception : " + e.getMessage());
        }
        LOGGER.info("Save file successfully");
    }

    @Override
    public void CreateFileJsonLocal(JsonObjectFollowup jsonObjectFollowup, String path) {
        LOGGER.info("Create Json File in Folder Parent");
        try {
            FileWriter file = new FileWriter(path + "/" + "PPX.json", StandardCharsets.UTF_8);
            LOGGER.info("path: " + path);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            file.write(ow.writeValueAsString(jsonObjectFollowup));
            file.close();
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
        }
    }

//    @Override
//    public void CreateFileJsonServer(JsonObjectFollowup jsonObjectFollowup, String path) {
//        LOGGER.info("Create Json File in Folder Parent Local");
//        try {
//            FileWriter file = new FileWriter(path + "\\"+"PPX.json");
//            LOGGER.info("path: " + path);
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            file.write(ow.writeValueAsString(jsonObjectFollowup));
//            file.close();
//        } catch (Exception e) {
//            LOGGER.error("Error: " + e.getMessage());
//        }
//    }

    @Override
    public void createFileAttachmentLocal(String claimRequestId, JsonObjectFollowup jsonObjectFollowup) throws IOException {
        LOGGER.info("Create file Attachment in folder local");
        FileOutputStream fileOutputStream = null;
        try {
            for (Attachments a : jsonObjectFollowup.getAttachments()) {
                String followUpCode = a.getFollowupCode();
                StringBuilder folderName = new StringBuilder();
                UUID uuid = UUID.randomUUID();
             //   folderName.append(claimRequestId).append("_").append(followUpCode).append("_").append(uuid);
                folderName.append(claimRequestId).append("_").append(uuid);
                String newFolderDir = config.getFolderParentAddLocal();
                LOGGER.info("newFolderDir: " + newFolderDir);
                String pathFolderName = newFolderDir + folderName;
                File folderParent = new File(pathFolderName);
                if (!folderParent.exists()) {
                    folderParent.mkdir();
                }
                CreateFileJsonLocal(jsonObjectFollowup, pathFolderName);
                String nameFileAttach = a.getFormId() + "_" + a.getName();
                String pathFile = pathFolderName + "/" + nameFileAttach;
                String data = a.getData();
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
