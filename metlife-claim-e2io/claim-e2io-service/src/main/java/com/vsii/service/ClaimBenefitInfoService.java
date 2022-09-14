package com.vsii.service;

import com.vsii.entity.ClaimBenefitInfoEntity;
import com.vsii.entity.ClaimRequestEntity;
import com.vsii.model.Diagnostic;
import com.vsii.model.JsonObject;

import java.util.List;

public interface ClaimBenefitInfoService {
    void Save(JsonObject jsonObject, Integer idClaimRequest);
}
