package com.vsii.service;

import com.vsii.model.JsonObject;
import com.vsii.model.JsonObjectFollowup;

import java.util.List;

public interface ClaimAdditionalBenefitService {
    List<Integer> Save(JsonObjectFollowup jsonObject, Integer idAdditional);
}
