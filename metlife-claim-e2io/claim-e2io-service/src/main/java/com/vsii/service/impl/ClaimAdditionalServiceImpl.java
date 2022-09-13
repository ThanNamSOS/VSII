package com.vsii.service.impl;

import com.vsii.entity.ClaimAdditionalEntity;
import com.vsii.model.*;
import com.vsii.repository.ClaimAdditionalRepository;
import com.vsii.service.ClaimAdditionalService;
import com.vsii.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaimAdditionalServiceImpl implements ClaimAdditionalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimAdditionalServiceImpl.class);
    @Autowired
    private ClaimAdditionalRepository claimAdditionalRepository;

    @Override
    public ClaimAdditionalEntity Save(JsonObjectFollowup jsonObject, ClaimModel claimModel) {
        LOGGER.info("Start save Claim Additional");
        ClaimAdditionalEntity additional = new ClaimAdditionalEntity();
        additional.setClaimId(jsonObject.getClaimId());
        additional.setClaimRequestId(jsonObject.getClaimRequestId());
        claimAdditionalRepository.save(additional);
        LOGGER.info("Start save Claim Additional successful");
        return additional;
    }
}
