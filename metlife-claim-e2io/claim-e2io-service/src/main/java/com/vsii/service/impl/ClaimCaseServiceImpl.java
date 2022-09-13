package com.vsii.service.impl;

import com.vsii.entity.ClaimCaseEntity;
import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;
import com.vsii.repository.ClaimCaseRepository;
import com.vsii.service.ClaimCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimCaseServiceImpl implements ClaimCaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimCaseServiceImpl.class);
    @Autowired
    private ClaimCaseRepository claimCaseRepository;

    @Override
    public void Save(JsonObject jsonObject, Integer idClaimRequest, ClaimModel claimModel) {
        LOGGER.info("Save Claim Case Start");
        ClaimCaseEntity claimCase = new ClaimCaseEntity();
        claimCase.setIdClaimRequest(idClaimRequest);
        claimCase.setClaimRequestId(jsonObject.getClaimRequestId());
        claimCase.setClaimID(claimModel.getClaimId());
        claimCase.setClaimRequestFrom(jsonObject.getClaimRequestForm());
        claimCaseRepository.save(claimCase);
        LOGGER.info("Save Claim Case successful");
    }
}
