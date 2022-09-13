package com.vsii.scheduler;

import com.vsii.config.ApplicationContextHolder;
import com.vsii.config.PropertiesConfig;
import com.vsii.enums.ErrorEnum;
import com.vsii.helper.FileChangeHelper;
import com.vsii.model.ErrorDetails;
import com.vsii.service.impl.FileServiceImpl;
import com.vsii.service.impl.ValidateServiceImpl;
import com.vsii.utils.JMSUtils;
import com.vsii.utils.SchedulerListenerUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Component
public class ScanAddScheduler extends BaseScheduler implements FileChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanAddScheduler.class);

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        List<File> lstFolder = FileChangeHelper.getFolderChange(changeSet);
        if (!CollectionUtils.isEmpty(lstFolder)) {
            LOGGER.info("Scan Add start at: " + LocalDateTime.now());
            if (jmsUtils == null) {
                jmsUtils = ApplicationContextHolder.getContext().getBean(JMSUtils.class);
            }
            if (validateService == null) {
                validateService = ApplicationContextHolder.getContext().getBean(ValidateServiceImpl.class);
            }
            if (propertiesConfig == null) {
                propertiesConfig = ApplicationContextHolder.getContext().getBean(PropertiesConfig.class);
            }
            if (fileService == null) {
                fileService = ApplicationContextHolder.getContext().getBean(FileServiceImpl.class);
            }
            String ScanAddError = fileService.buildFolderPath(propertiesConfig.getFolderError(),
                    propertiesConfig.getFolderScanADDPPX());
            File fileError = null;
            for (File file : lstFolder) {
                ErrorDetails error = validateService.validateNameFolder(file.getName(),
                        propertiesConfig.getFolderDelimiterWebserviceAdd(),
                        propertiesConfig.getFieldLengthWebserviceAdd());
                if (error == null) {
                    try {
                        jmsUtils.sendMessageScanAdd(file.getAbsolutePath());
                    } catch (Exception e) {
                        fileError = new File(fileService.buildFolderPath(ScanAddError, file.getName()));
                        File moveFolder = fileService.moveFolder(file, fileError);
                        fileService.buildErrorFile(moveFolder.getAbsolutePath(),
                                SchedulerListenerUtils.buildErrorModel(ErrorEnum.FOLDER_ERROR.getErrorCode(), e));
                        LOGGER.error("Import folder error: " + ExceptionUtils.getStackTrace(e));
                    }
                } else {
                    fileError = new File(fileService.buildFolderPath(ScanAddError, file.getName()));
                    File moveFolder = fileService.moveFolder(file, fileError);
                    fileService.buildErrorFile(moveFolder.getAbsolutePath(),
                            SchedulerListenerUtils.buildErrorModel(ErrorEnum.FOLDER_ERROR.getErrorCode(), error));
                }
            }
            LOGGER.info("Scan Add and at: " + LocalDateTime.now());
        }
    }
}

