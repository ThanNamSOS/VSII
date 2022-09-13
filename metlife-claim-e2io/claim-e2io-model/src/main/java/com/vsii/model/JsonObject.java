package com.vsii.model;

import lombok.Data;

@Data
public class JsonObject {
    private String source;
    private String claimRequestId;
    private String policyNumber="";
    private String submissionTimestamp="";
    private String claimRequestForm="";
    private Owner owner=new Owner();
    private LifeAssured lifeAssured;
    private PaymentInfo paymentInfo;
    private Fatca fatca;

}
