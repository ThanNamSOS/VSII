package com.vsii.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pId;
    //yyyyMMdd HH:mm:ss.SSS
    private String startTime;
    private String appNo;
    private String folderName;
    private String path;
    //yyyyMMdd HH:mm:ss.SSS
    private String endTime;
    private String fileCount;

}
