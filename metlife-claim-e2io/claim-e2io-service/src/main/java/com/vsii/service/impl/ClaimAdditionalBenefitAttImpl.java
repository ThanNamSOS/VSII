package com.vsii.service.impl;

import com.vsii.entity.ClaimAdditionalBenefitAttEntity;
import com.vsii.model.*;
import com.vsii.repository.ClaimAdditionalBenefitAttRepository;
import com.vsii.service.ClaimAdditionalBenefitAttService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimAdditionalBenefitAttImpl implements ClaimAdditionalBenefitAttService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimAdditionalBenefitAttImpl.class);
    @Autowired
    private ClaimAdditionalBenefitAttRepository benefitAttRepository;
    @Override
    public void Save(JsonObjectFollowup jsonObject, Integer additional) {
        LOGGER.info("Start save Claim Additional Benefit Att");
        List<Attachments> attachments = jsonObject.getAttachments();
        if(attachments.size() > 0){
            for (Attachments att :attachments) {
                ClaimAdditionalBenefitAttEntity entity = new ClaimAdditionalBenefitAttEntity();
                entity.setClaimAdditional(additional);
                entity.setFileName(att.getName());
                entity.setFileType(att.getContentType());
                entity.setFollowUpCode(att.getFollowupCode());
                benefitAttRepository.save(entity);
                LOGGER.info("Save Claim Additional Benefit Att Successfully");
            }
        }
    }
}
