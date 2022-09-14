package com.vsii.service.impl;


import com.vsii.entity.ClaimBenefitInfoEntity;
import com.vsii.model.ClaimBenefit;
import com.vsii.model.Diagnostic;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimBenefitInfoRepository;
import com.vsii.service.ClaimBenefitAttService;
import com.vsii.service.ClaimBenefitInfoService;
import com.vsii.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimBenefitInfoServiceImpl implements ClaimBenefitInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimBenefitInfoServiceImpl.class);
    @Autowired
    private ClaimBenefitInfoRepository claimBenefitInfoRepository;
    @Autowired
    ClaimBenefitAttService claimBenefitAttService;
    @Override
    public void Save(JsonObject jsonObject, Integer idClaimRequest) {
        LOGGER.info("Save Claim Benefit Info Start");
        List<ClaimBenefit> claimBenefits = jsonObject.getLifeAssured().getClaimBenefits();
        for (ClaimBenefit claimBenefit: claimBenefits) {
            Diagnostic diagnostic = claimBenefit.getDiagnostic();
            ClaimBenefitInfoEntity claimBenefitInfo = new ClaimBenefitInfoEntity();
            claimBenefitInfo.setIdClaimRequest(idClaimRequest);
            claimBenefitInfo.setBenCode(claimBenefit.getBenCode());

            claimBenefitInfo.setStartDate(DateUtils.convertDate(claimBenefit.getStartDate()));
            claimBenefitInfo.setEndDate(DateUtils.convertDate(claimBenefit.getEndDate()));
            claimBenefitInfo.setDiagnosticCode(diagnostic.getCode());
            claimBenefitInfo.setDiagnosticDescripTion(diagnostic.getDescription());
            claimBenefitInfoRepository.save(claimBenefitInfo);

            claimBenefitAttService.Save(jsonObject, claimBenefitInfo.getId());
            LOGGER.info("Save Claim Benefit Info successful");
        }
    }
}
