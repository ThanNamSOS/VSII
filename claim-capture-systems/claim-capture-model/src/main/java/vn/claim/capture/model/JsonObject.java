package vn.claim.capture.model;

import lombok.Data;

import java.util.Date;

@Data
public class JsonObject {
    private String source;
    private String claimRequestId;
    private String policyNumber;
    private String submissionTimestamp="";
    private String claimRequestForm="";
    private Owner owner;
    private LifeAssured lifeAssured;
    private PaymentInfo paymentInfo;
    private Fatca fatca;

}
