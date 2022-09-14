package com.vsii.service;

import com.vsii.entity.ClaimRequestEntity;
import com.vsii.model.JsonObject;

public interface ClaimRequestService {
    ClaimRequestEntity save(JsonObject jsonObject);
}
