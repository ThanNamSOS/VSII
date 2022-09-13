package com.vsii.service.impl;

import com.vsii.entity.ClaimBenefitAtt;
import com.vsii.model.Attachment;
import com.vsii.model.ClaimBenefit;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimBenefitAttRepository;
import com.vsii.service.ClaimBenefitAttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimBenefitAttServiceImpl implements ClaimBenefitAttService {

    @Autowired
    ClaimBenefitAttRepository benefitAttRepository;
    @Override
    public void Save(JsonObject jsonObject, Integer idClaimBenefit) {

        List<ClaimBenefit> benfitModel = jsonObject.getLifeAssured().getClaimBenefits();
        for (ClaimBenefit claimBenefit:  benfitModel ) {
            for (Attachment attachment: claimBenefit.getAttachments() ) {
                ClaimBenefitAtt claimBenefitAtt = new ClaimBenefitAtt();
                claimBenefitAtt.setIdClaimBen(idClaimBenefit);
                claimBenefitAtt.setBenefitName(attachment.getName());
                claimBenefitAtt.setFileType(attachment.getContentType());
                benefitAttRepository.save(claimBenefitAtt);
            }
        }
    }
}
