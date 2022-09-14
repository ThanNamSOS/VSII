package com.vsii.service.impl;

import com.vsii.entity.ClaimBenefitAttEntity;
import com.vsii.model.Attachment;
import com.vsii.model.ClaimBenefit;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimBenefitAttRepository;
import com.vsii.service.ClaimBenefitAttService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimBenefitAttServiceImpl implements ClaimBenefitAttService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimBenefitAttServiceImpl.class);

    @Autowired
    ClaimBenefitAttRepository benefitAttRepository;

    @Override
    public void Save(JsonObject jsonObject, Integer idClaimBenefit) {
        LOGGER.info("Save Claim Benefit att Start");
        List<ClaimBenefit> benfitModel = jsonObject.getLifeAssured().getClaimBenefits();
        if (benfitModel.size() > 0) {
            for (ClaimBenefit claimBenefit : benfitModel) {
                for (Attachment attachment : claimBenefit.getAttachments()) {
                    ClaimBenefitAttEntity claimBenefitAtt = new ClaimBenefitAttEntity();
                    claimBenefitAtt.setIdClaimBen(idClaimBenefit);
                    claimBenefitAtt.setFileName(attachment.getName());
                    claimBenefitAtt.setFileType(attachment.getContentType());
                    claimBenefitAtt.setFormId(attachment.getFormId());
                    benefitAttRepository.save(claimBenefitAtt);
                    LOGGER.info("Save Claim Benefit att successful");
                }
            }
        }
    }
}
