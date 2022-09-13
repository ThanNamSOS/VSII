package vn.claim.capture.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClaimBenefit {
    private String benCode;
    private String startDate;
    private String endDate;
    private Diagnostic diagnostic;
    private List<Attachment> attachments;
}
