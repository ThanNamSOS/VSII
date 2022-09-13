package vn.systems.claim.services.capture.service;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import vn.claim.capture.model.JsonObject;
import vn.claim.capture.model.JsonObjectFollowup;
import vn.claim.capture.model.ResponseObject;

import java.net.MalformedURLException;

public interface FileService {
    ResponseObject validateJsonObject(JsonObject jsonObject);

    ResponseObject validateJsonObjectFollowUp(JsonObjectFollowup jsonObjectFollowup);

}
