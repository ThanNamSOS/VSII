package com.vsii.model;

import lombok.Data;

@Data
public class ResponseObject {
    private Integer code;
    private String message;
    private String description;
    private Object data;
}
