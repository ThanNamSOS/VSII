package com.vsii.service.impl;

import com.vsii.entity.ClaimBenefitEntity;
import com.vsii.model.Attachment;
import com.vsii.model.ClaimBenefit;
import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimBenefitAttRepository;
import com.vsii.repository.ClaimBenefitRepository;
import com.vsii.service.ClaimBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimBenefitServiceImpl implements ClaimBenefitService {

    @Autowired
    private ClaimBenefitRepository claimBenefitRepository;
    @Autowired
    ClaimBenefitAttRepository benefitAttRepository;
    @Override
    public ClaimBenefitEntity finbyBencode(String bencode) {
        return claimBenefitRepository.findByBencode(bencode);
    }

    @Override
    public  List<Integer> Save(JsonObject jsonObject) {
        List<Integer> bencodes = new ArrayList<>();
        ClaimBenefitEntity benefitEntity = new ClaimBenefitEntity();
        List<ClaimBenefit> benfitModel = jsonObject.getLifeAssured().getClaimBenefits();
        for (ClaimBenefit ClaimBenefit:  benfitModel ) {
            benefitEntity.setBenefitCode(ClaimBenefit.getBenCode());
            benefitEntity.setBenefitName(ClaimBenefit.getDiagnostic().getDescription());
            claimBenefitRepository.save(benefitEntity);
            bencodes.add(benefitEntity.getId());
        }
        return bencodes;
    }
}
