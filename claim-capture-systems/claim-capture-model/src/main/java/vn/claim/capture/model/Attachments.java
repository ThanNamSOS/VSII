package vn.claim.capture.model;

import lombok.Data;

@Data
public class Attachments {
    private String name;
    private String contentType="";
    private String data="";
    private String formId;
    private String followupCode;
    private String localPath="";
}
