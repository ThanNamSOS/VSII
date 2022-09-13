package com.vsii.model;

import lombok.Data;

import java.util.List;

@Data
public class ClaimBenefit {
    private String claimId;
    private String benCode="";
    private String startDate;
    private String endDate;
    private Diagnostic diagnostic = new Diagnostic();
    private List<Attachment> attachments;
}
