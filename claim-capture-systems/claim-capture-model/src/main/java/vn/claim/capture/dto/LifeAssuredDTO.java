package vn.claim.capture.dto;

import lombok.Data;
import vn.claim.capture.model.ClaimBenefit;

import java.util.List;

@Data
public class LifeAssuredDTO {
    private String clientId = "";
    private String clientNumber = "";
    private String lifeNo = "";
    private String name = "";
    private String citizenId = "";
    private List<ClaimBenefitDTO> claimBenefits;
}
