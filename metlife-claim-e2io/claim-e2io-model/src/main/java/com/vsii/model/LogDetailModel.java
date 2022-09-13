package com.vsii.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogDetailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pId;
    private String processName;
    // yyyyMMdd HH:mm:ss.SSS
    private String startTime;
    // yyyyMMdd HH:mm:ss.SSS
    private String endTime;

    public LogDetailModel(String pId) {
        this.pId = pId;
    }

    public LogDetailModel() {
    }

    public LogDetailModel(String pId, String processName, String startTime, String endTime) {
        this.pId = pId;
        this.processName = processName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
