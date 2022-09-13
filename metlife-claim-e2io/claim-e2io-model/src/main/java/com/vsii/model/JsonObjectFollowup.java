package com.vsii.model;

import lombok.Data;

import java.util.List;

@Data
public class JsonObjectFollowup {
    private String claimRequestId;
    private String claimId;
    private List<Attachments> attachments;
}
