package com.vsii.model;

import lombok.Data;

@Data
public class FileModel {
    private String filePath;
    private String formId;
    //YYYYMMDD
    private String dateUpload;
    private String displayName;
    private String applicationNo;
    private String batchNo;
    private String boxNo;
    private String scanUser;
    private String qcUser;
    private String contestCode;
}
