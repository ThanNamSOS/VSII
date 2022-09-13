package vn.claim.capture.dto;

import lombok.Data;
import vn.claim.capture.model.Fatca;
import vn.claim.capture.model.Owner;
import vn.claim.capture.model.PaymentInfo;
@Data
public class JsonObjectDTO {
    private String source;
    private String claimRequestId;
    private String policyNumber;
    private String submissionTimestamp="";
    private String claimRequestForm="";
    private Owner owner;
    private LifeAssuredDTO lifeAssured;
    private PaymentInfo paymentInfo;
    private Fatca fatca;
}
