package vn.systems.claim.services.capture.service;

import vn.claim.capture.dto.JsonObjectDTO;
import vn.claim.capture.model.ClaimBenefit;
import vn.claim.capture.model.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ClaimService {
    boolean createFolderParentToServer(JsonObject jsonObject);

    boolean createFileAttachment(ClaimBenefit claimBenefit, String claimRequestId, JsonObject jsonObject, int count) throws IOException;

    void createFolderParentToLocal(JsonObject jsonObject);

    void CreateFileJsonLocal(JsonObjectDTO jsonObjectDTO, String path, String claimId);

    void createFileAttachmentLocal(ClaimBenefit claimBenefit, String claimRequestId, JsonObject jsonObject,int count) throws IOException;


}
