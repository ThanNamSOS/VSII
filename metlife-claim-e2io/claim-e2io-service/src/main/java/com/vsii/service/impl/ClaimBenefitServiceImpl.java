package com.vsii.service.impl;

import com.vsii.entity.ClaimBenefitEntity;
import com.vsii.model.Attachment;
import com.vsii.model.ClaimBenefit;
import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimBenefitAttRepository;
import com.vsii.repository.ClaimBenefitRepository;
import com.vsii.service.ClaimBenefitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimBenefitServiceImpl implements ClaimBenefitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimBenefitServiceImpl.class);

    @Autowired
    private ClaimBenefitRepository claimBenefitRepository;
    @Autowired
    ClaimBenefitAttRepository benefitAttRepository;

    @Override
    public ClaimBenefitEntity finbyBencode(String bencode) {
        return claimBenefitRepository.findByBencode(bencode);
    }

    @Override
    public List<Integer> Save(JsonObject jsonObject) {
        LOGGER.info("Save Claim Benefit Start");
        List<Integer> bencodes = new ArrayList<>();
        List<ClaimBenefit> benfitModel = jsonObject.getLifeAssured().getClaimBenefits();
        if (benfitModel.size() > 0) {
            for (ClaimBenefit ClaimBenefit : benfitModel) {
                ClaimBenefitEntity benefitEntity = new ClaimBenefitEntity();
                benefitEntity.setBenefitCode(ClaimBenefit.getBenCode());
                claimBenefitRepository.save(benefitEntity);
                LOGGER.info("Save Claim Benefit successful");
                bencodes.add(benefitEntity.getId());
            }
        }
        return bencodes;
    }
}
