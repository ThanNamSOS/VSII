package com.vsii.service;

import com.vsii.entity.Form;
import com.vsii.model.ErrorModel;

import java.io.File;
import java.util.List;

public interface FileService {
    String buildFolderPath(String folderParent, String folderChild);

    List<Form> listForm();

    File moveFolder(File sourceFile, File targetFile);



    void buildErrorFile(String pathFolder, ErrorModel errorModel);
}
