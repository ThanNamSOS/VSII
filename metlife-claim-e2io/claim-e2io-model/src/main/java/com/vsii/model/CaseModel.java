package com.vsii.model;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class CaseModel {
    private IndexesModel indexes;
    private List<FileModel> attachments;
    private ErrorModel errorInfo;
    private List<File> errorFile;
}
