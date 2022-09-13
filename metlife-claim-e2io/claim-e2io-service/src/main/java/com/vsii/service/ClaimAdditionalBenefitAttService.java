package com.vsii.service;

import com.vsii.model.JsonObject;
import com.vsii.model.JsonObjectFollowup;

public interface ClaimAdditionalBenefitAttService {
    void Save(JsonObjectFollowup jsonObject, Integer additional);
}
