package vn.util;

import com.google.gson.Gson;
import vn.claim.capture.dto.ClaimDTO;
import vn.claim.capture.model.JsonObject;
import vn.claim.capture.model.JsonObjectFollowup;
import vn.claim.capture.model.ResponseObject;

import static vn.constants.Constants.*;

public class Utils {
    public static ResponseObject responseSuccess(JsonObject jsonObject) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1000);
        responseObject.setMessage(MESSAGE);
        responseObject.setDescription(SUCCESS);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseSuccessJsonObjectFolowUp(JsonObjectFollowup jsonObjectFollowup) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1000);
        responseObject.setMessage(MESSAGE);
        responseObject.setDescription(SUCCESS);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseCreateFileLocalSuccess(JsonObject jsonObject) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1000);
        responseObject.setMessage(MESSAGE);
        responseObject.setDescription(SUCCESS);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseCreateFileLocalSuccessJsonObjectFollowUp(JsonObjectFollowup jsonObjectFollowup) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1000);
        responseObject.setMessage(MESSAGE);
        responseObject.setDescription(SUCCESS);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectFormId(JsonObject jsonObject, String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1003);
        responseObject.setMessage("Invalid Form ID: ");
        responseObject.setDescription(FORM_ID_VALIDATE);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObject(String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1004);
        responseObject.setMessage("Invalid FolowUp Code: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectInputString(JsonObject jsonObject, String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(400);
        responseObject.setMessage("Invalid input data: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectInputData(JsonObject jsonObject, String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1002);
        responseObject.setMessage("Invalid input data: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectBenCode(String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1006);
        responseObject.setMessage("Invalid input data: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectAttach(String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1007);
        responseObject.setMessage("Invalid input data: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }

    public static ResponseObject responseValidateJsonObjectFolowUpInputData(String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1002);
        responseObject.setMessage("Invalid input data: ");
        responseObject.setDescription(DATA + message + invalid);
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        return responseObject;
    }


    public static ResponseObject responseUnSuccess(JsonObject jsonObject) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1005);
        responseObject.setMessage("Save File To Server UnSuccess");
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        responseObject.setDescription("Request Body Json Invalid");
        return responseObject;
    }

    public static ResponseObject responseUnSuccessJsonObjectFollowUp(JsonObjectFollowup jsonObjectFollowup) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(1005);
        responseObject.setMessage("Save File To Server UnSuccess");
        ClaimDTO claimDTO = new ClaimDTO();
        responseObject.setData(claimDTO);
        responseObject.setDescription("Request Body Json Invalid");
        return responseObject;
    }

}
