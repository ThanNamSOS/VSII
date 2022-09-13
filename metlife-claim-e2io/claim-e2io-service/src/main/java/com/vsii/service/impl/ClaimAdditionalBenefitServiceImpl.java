package com.vsii.service.impl;

import com.vsii.entity.ClaimAdditionalBenefitEntity;
import com.vsii.model.*;
import com.vsii.repository.ClaimAdditinalBenefitRepository;
import com.vsii.service.ClaimAdditionalBenefitService;
import com.vsii.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ClaimAdditionalBenefitServiceImpl implements ClaimAdditionalBenefitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimAdditionalBenefitServiceImpl.class);
    @Autowired
    private ClaimAdditinalBenefitRepository additinalBenefitRepository;

    @Override
    public List<Integer> Save(JsonObjectFollowup jsonObject, Integer idAdditional) {
        LOGGER.info("Start Save Claim Additinal Benefit");
        List<Integer> idAddBens = new ArrayList<>();
        for (Attachments attachment : jsonObject.getAttachments()) {
            ClaimAdditionalBenefitEntity additionalBenefit = new ClaimAdditionalBenefitEntity();
            additionalBenefit.setIdClaimAdditional(idAdditional);
            additinalBenefitRepository.save(additionalBenefit);
            idAddBens.add(additionalBenefit.getId());
        }
        return idAddBens;
    }
}
