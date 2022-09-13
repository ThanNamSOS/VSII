package com.vsii.service;

import com.vsii.entity.ClaimBenefitEntity;
import com.vsii.model.JsonObject;

import java.util.List;

public interface ClaimBenefitService {
    public ClaimBenefitEntity finbyBencode(String bencode);

    List<Integer> Save(JsonObject jsonObject);

}
