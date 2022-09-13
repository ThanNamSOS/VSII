package vn.systems.claim.services.capture.service;

import vn.claim.capture.model.ClaimBenefit;
import vn.claim.capture.model.JsonObject;
import vn.claim.capture.model.JsonObjectFollowup;

import java.io.IOException;

public interface FileAddService {
    boolean createFolderParentToServer(JsonObjectFollowup jsonObjectFollowup);

    boolean createFileAttachment(String claimRequestId, JsonObjectFollowup jsonObjectFollowup) throws IOException;

    void createFolderParentToLocal(JsonObjectFollowup jsonObjectFollowup);

    void CreateFileJsonLocal(JsonObjectFollowup jsonObjectFollowup, String path);
//    void CreateFileJsonServer(JsonObjectFollowup jsonObjectFollowup, String path);

    void createFileAttachmentLocal(String claimRequestId, JsonObjectFollowup jsonObjectFollowup) throws IOException;

}
