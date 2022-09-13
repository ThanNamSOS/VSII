package com.vsii.appscan;

import com.vsii.config.PropertiesConfig;

import com.vsii.enums.ErrorEnum;
import com.vsii.model.ErrorDetails;
import com.vsii.service.ValidateService;
import com.vsii.service.impl.FileServiceImpl;
import com.vsii.utils.JMSUtils;
import com.vsii.utils.SchedulerListenerUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class    ClaimAppScan {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimAppScan.class);
    @Autowired
    protected JMSUtils jmsUtils;

    @Autowired
    protected PropertiesConfig propertiesConfig;

    @Autowired
    protected FileServiceImpl fileService;

    @Autowired
    protected ValidateService validateService;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void job() {
        LOGGER.info("Applicaiton Scan All Folder start at: " + LocalDateTime.now());
        try {
            //new
            scanFolder(propertiesConfig.getFolderWebserviceNC(), propertiesConfig.getFolderDelimiterWebserviceNC(), propertiesConfig.getFieldLengthWebserviceNC(), propertiesConfig.getTopicNameNew());
            //Wadd
            scanFolder(propertiesConfig.getFolderWebserviceADD(), propertiesConfig.getFolderDelimiterWebserviceAdd(), propertiesConfig.getFieldLengthWebserviceAdd(), propertiesConfig.getTopicNameAdd());
            //ScanNew
            scanFolder(propertiesConfig.getFolderScanNCPPX(), propertiesConfig.getFolderDelimiterScanNew(), propertiesConfig.getFieldLengthScanNew(), propertiesConfig.getTopicNameScanNew());
            //ScanAdd
            scanFolder(propertiesConfig.getFolderScanADDPPX(), propertiesConfig.getFolderDelimiterScanAdd(), propertiesConfig.getFieldLengthScanAdd(), propertiesConfig.getTopicNameScanADD());
            //import
            scanFolder(propertiesConfig.getFolderImportPPX(), propertiesConfig.getFolderDelimiterImport(), propertiesConfig.getFieldLengthImport(), propertiesConfig.getTopicNameImport());

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
        LOGGER.info("Applicaiton Scan All Folder end at: " + LocalDateTime.now());
    }

    public void scanFolder(String folderName, String delimiter, int filedLength, String topic) {
        LOGGER.info("Start scan folder: " + folderName);
        String realse = fileService.buildFolderPath(propertiesConfig.getFolderRelease(), folderName);
        String error = fileService.buildFolderPath(propertiesConfig.getFolderError(), folderName);
        File folder = new File(realse);
        List<File> collect = Stream.of(folder.listFiles()).filter(File::isDirectory).collect(Collectors.toList());
        File fileError = null;
        for (File file : collect) {
            ErrorDetails errorDetails = validateService.validateNameFolder(file.getName(), delimiter, filedLength);
            if (errorDetails == null) {
                try {
                    LOGGER.info("File path send: " + file.getAbsolutePath());
                    switch (topic) {
                        case "claim-capture-new":
                            jmsUtils.sendMessage(file.getAbsolutePath());
                            break;
                        case "claim-capture-add":
                            jmsUtils.sendMessageAdd(file.getAbsolutePath());
                        case "claim-capture-scan-new":
                            jmsUtils.sendMessageScanNew(file.getAbsolutePath());
                            break;
                        case "claim-capture-scan-add":
                            jmsUtils.sendMessageScanAdd(file.getAbsolutePath());
                        case "claim-capture-import":
                            jmsUtils.sendMessageImport(file.getAbsolutePath());
                            break;
                    }
                } catch (Exception e) {
                    fileError = new File(fileService.buildFolderPath(error, file.getName()));
                    File moveFolder = fileService.moveFolder(file, fileError);
                    fileService.buildErrorFile(moveFolder.getAbsolutePath(), SchedulerListenerUtils.buildErrorModel(ErrorEnum.INTERNAL_ERROR.getErrorCode(), e));
                    LOGGER.error("Import folder error: " + ExceptionUtils.getRootCauseMessage(e));
                }
            } else {
                fileError = new File(fileService.buildFolderPath(error, file.getName()));
                File moveFolder = fileService.moveFolder(file, fileError);
                fileService.buildErrorFile(moveFolder.getAbsolutePath(), SchedulerListenerUtils.buildErrorModel(ErrorEnum.FOLDER_ERROR.getErrorCode(), errorDetails));
            }
        }
        LOGGER.info("End scan folder: " + folderName);
    }
}
