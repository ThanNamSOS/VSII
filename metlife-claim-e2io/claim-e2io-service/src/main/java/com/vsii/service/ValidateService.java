package com.vsii.service;

import com.vsii.entity.Form;
import com.vsii.model.ErrorDetails;

import java.io.File;
import java.util.Map;

public interface ValidateService {
    ErrorDetails validateNameFolder(String folderName, String delimiter, int fieldLength);

    ErrorDetails validateFile(File file, String[] lstExtensions, String delimiter, Map<String, Form> mapForm);
}
