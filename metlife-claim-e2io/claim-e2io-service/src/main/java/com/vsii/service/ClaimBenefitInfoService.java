package com.vsii.service;

import com.vsii.entity.ClaimRequestEntity;
import com.vsii.model.Diagnostic;
import com.vsii.model.JsonObject;

public interface ClaimBenefitInfoService {
    void Save(JsonObject jsonObject, Integer idClaimRequest);
}
