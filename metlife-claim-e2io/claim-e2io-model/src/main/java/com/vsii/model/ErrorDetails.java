package com.vsii.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class ErrorDetails {
    private String errorRootCause;
    private String errorStackTrace;
}
