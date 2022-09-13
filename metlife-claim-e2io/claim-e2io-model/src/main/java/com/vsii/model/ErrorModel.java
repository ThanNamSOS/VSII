package com.vsii.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "ErrorInfo")
public class ErrorModel {
    private String errorCode;
    private List<ErrorDetails> errorDetails;
}
