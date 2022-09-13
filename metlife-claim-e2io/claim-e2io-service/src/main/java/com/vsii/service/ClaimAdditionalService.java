package com.vsii.service;

import com.vsii.entity.ClaimAdditionalEntity;
import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;
import com.vsii.model.JsonObjectFollowup;

public interface ClaimAdditionalService {
    ClaimAdditionalEntity Save(JsonObjectFollowup jsonObject, ClaimModel claimModel);
}
