package vn.claim.capture.dto;

import lombok.Data;
import vn.claim.capture.model.Attachment;
import vn.claim.capture.model.Diagnostic;

import java.util.List;
@Data
public class ClaimBenefitDTO {
    private String claimId;
    private String benCode;
    private String startDate;
    private String endDate;
    private Diagnostic diagnostic;
    private List<Attachment> attachments;
}
