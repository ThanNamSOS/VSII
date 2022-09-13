package com.vsii.service;

import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;

public interface ClaimCaseService {
     void Save(JsonObject jsonObject,Integer idClaimRequest, ClaimModel claimModel);
}
