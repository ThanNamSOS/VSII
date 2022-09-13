package com.vsii.service.impl;

import com.vsii.entity.Form;
import com.vsii.enums.ErrorEnum;
import com.vsii.model.ErrorDetails;
import com.vsii.service.ValidateService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@Service
public class ValidateServiceImpl implements ValidateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateServiceImpl.class);

    @Override
    public ErrorDetails validateNameFolder(String folderName, String delimiter, int fieldLength) {
        LOGGER.info("Start Validate Folder: " + folderName);
        ErrorDetails error = null;
        try {
            String[] inputParams = folderName.split(delimiter, -1);
            if (inputParams.length != fieldLength || StringUtils.isEmpty(inputParams[0])) {
                error = new ErrorDetails();
                error.setErrorRootCause(folderName);
                error.setErrorStackTrace(ErrorEnum.INVALID_FOLDER_NAME.getErrorDesc());
            }
        } catch (Exception e) {
            LOGGER.error("Validate Folder Error: " + ExceptionUtils.getRootCauseMessage(e));
            error = new ErrorDetails();
            error.setErrorRootCause(folderName);
            error.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
        }

        LOGGER.info("End Validate Folder: " + folderName);
        return error;
    }

    // true if extension file match with list file extension
    // list extension configuration in properties file parameter
    // com.bml.e2io.file-extensions
    @Override
    public ErrorDetails validateFile(File file, String[] lstExtensions, String delimiter, Map<String, Form> mapForm) {
        LOGGER.info("Start Validate file: " + file.getAbsolutePath());
        ErrorDetails error = null;
        boolean checkFile = false;
        LOGGER.info("List Extensions file: " + lstExtensions);

        if (ArrayUtils.isEmpty(lstExtensions)) {
            checkFile = true;
            LOGGER.info("List Extensions file null. Not check Extensions file!!!");
        } else {
            LOGGER.info("Start Check extension file: " + file.getAbsolutePath());
            String extension = FilenameUtils.getExtension(file.getName()).toUpperCase();
            LOGGER.info("Extension file= " + extension);
            checkFile = Arrays.stream(lstExtensions).anyMatch(extension::equals);
            LOGGER.info("End Check extension file: " + file.getAbsolutePath());
        }
        LOGGER.info("Check Extension file = " + checkFile);
        String fileName = file.getName();
        if (checkFile) {
            LOGGER.info("Start Check FormID file: " + file.getAbsolutePath());
            try {
                String[] fileParams = fileName.split(delimiter, -1);
                if (StringUtils.isEmpty(fileParams[0])) {
                    checkFile = false;
                } else {
                    checkFile = mapForm.containsKey(fileParams[0]);
                }
                if (!checkFile) {
                    error = new ErrorDetails();
                    error.setErrorRootCause(fileName);
                    error.setErrorStackTrace(ErrorEnum.INVALID_FORM_ID.getErrorDesc());
                }
            } catch (Exception e) {
                LOGGER.error("Validate File Error: " + ExceptionUtils.getStackTrace(e));
                error = new ErrorDetails();
                error.setErrorRootCause(fileName);
                error.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
            }
            LOGGER.info("End Check FormID file: " + file.getAbsolutePath());
        } else {
            error = new ErrorDetails();
            error.setErrorRootCause(fileName);
            error.setErrorStackTrace(ErrorEnum.INVALID_FILE_EXTENSION.getErrorDesc());
        }

        LOGGER.info("Check FormID file = " + checkFile);
        LOGGER.info("End Validate file: " + file.getAbsolutePath());
        return error;
    }
}
