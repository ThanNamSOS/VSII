package vn.systems.claim.services.capture.service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.claim.capture.model.*;
import vn.commons.Commons;
import vn.constants.Constants;
import vn.systems.claim.services.capture.service.FileService;
import vn.util.Utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    @Override
    public ResponseObject validateJsonObject(JsonObject jsonObject) {
        logger.info("SOURCE: " + jsonObject.getSource());
        String source = jsonObject.getSource().toUpperCase();
        String claimRequestId = jsonObject.getClaimRequestId().toUpperCase();
        String policyNumber = jsonObject.getPolicyNumber().toUpperCase();
        String clientNumber = jsonObject.getOwner().getClientNumber();
        String lifeNo = jsonObject.getLifeAssured().getLifeNo();
        String mode = jsonObject.getPaymentInfo().getMode();
        String submissionTimestamp = jsonObject.getSubmissionTimestamp();
        String receiverCidIssueDate = jsonObject.getPaymentInfo().getReceiverCidIssueDate();
//        own
        String ownClientId = jsonObject.getOwner().getClientId().toUpperCase();
        String ownClientNumber = jsonObject.getOwner().getClientNumber().toUpperCase();
        String ownName = jsonObject.getOwner().getName().toUpperCase();
        String ownCitizenId = jsonObject.getOwner().getCitizenId().toUpperCase();
// paymentInfo
        String paymenInfoMode = jsonObject.getPaymentInfo().getMode().toUpperCase();
        if (!source.equals(Constants.SOURCE) || Commons.isNullOrEmpty(source)) {
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.SOURCE_);
            responseObject.setMessage(responseObject.getMessage() + responseObject.getCode());

            return responseObject;
        }
        if (Commons.isNullOrEmpty(claimRequestId)) {
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.claimRequestId);
            responseObject.setMessage(responseObject.getMessage() + responseObject.getCode());

            return responseObject;
        }
        if(Commons.isNullOrEmpty(policyNumber)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.policyNumber);
            responseObject.setMessage(responseObject.getMessage() + "policyNumber");
            return responseObject;
        }
        if (Commons.isNullOrEmpty(clientNumber)) {
            jsonObject.getOwner().setClientNumber("");
        }
        if (Commons.isNullOrEmpty(lifeNo)) {
            jsonObject.getLifeAssured().setLifeNo("");
        }
        if (Commons.isNullOrEmpty(mode)) {
            jsonObject.getPaymentInfo().setMode("");
        }
//  validate submissionTimestamp
        if (!Commons.isTimeStamp(submissionTimestamp)) {
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.submissionTimestamp);
            responseObject.setMessage(responseObject.getMessage() + "submissionTimestamp");
            return responseObject;
        }
        if (Commons.isNullOrEmpty(submissionTimestamp)) {
            jsonObject.setSubmissionTimestamp("");
        }
//  validate startDate,Enddate
        if (jsonObject.getLifeAssured().getClaimBenefits().size() > 0) {
            for (int i = 0; i < jsonObject.getLifeAssured().getClaimBenefits().size(); i++) {
                //            validate start date
                if (!Commons.isDateCorrect(jsonObject.getLifeAssured().getClaimBenefits().get(i).getStartDate())) {
                    ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.startDate);
                    responseObject.setMessage(responseObject.getMessage() + "startDate");
                    return responseObject;
                }
                if (Commons.isNullOrEmpty(jsonObject.getLifeAssured().getClaimBenefits().get(i).getStartDate())) {
                    jsonObject.getLifeAssured().getClaimBenefits().get(i).setStartDate("");
                }
                //            validate end date
                if (!Commons.isDateCorrect(jsonObject.getLifeAssured().getClaimBenefits().get(i).getEndDate())) {
                    ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.endDate);
                    responseObject.setMessage(responseObject.getMessage() + "endDate");
                    return responseObject;
                }
                if (Commons.isNullOrEmpty(jsonObject.getLifeAssured().getClaimBenefits().get(i).getEndDate())) {
                    jsonObject.getLifeAssured().getClaimBenefits().get(i).setEndDate("");
                }
            }
        }
// 8.Require 'Bencode'  + trường 'name' trong attachment khi có tài liệu
        List<ClaimBenefit> claimBenefitList = jsonObject.getLifeAssured().getClaimBenefits();
        if(claimBenefitList.size() < 1){
            ResponseObject responseObject = Utils.responseValidateJsonObjectBenCode(Constants.benCode);
            responseObject.setMessage(responseObject.getMessage() + "bencode isEmpty");
            return responseObject;
        }
        if(claimBenefitList.size() > 0){
            for(int i=0;i<claimBenefitList.size();i++){
//  validate diagnostic code
                String diagnosticCode = claimBenefitList.get(i).getDiagnostic().getCode().toUpperCase();
                if(Commons.isNullOrEmpty(diagnosticCode)){
                    ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject,Constants.diagnosticCode);
                    responseObject.setMessage(responseObject.getMessage() + "diagnostic code");
                    return responseObject;
                }
                if(claimBenefitList.get(i).getAttachments().size() < 1){
                    ResponseObject responseObject = Utils.responseValidateJsonObjectAttach(Constants.attachments);
                    responseObject.setMessage(responseObject.getMessage() + "attachment");
                    return responseObject;
                }
                if(claimBenefitList.get(i).getAttachments().size() > 0){
                    for(int j=0;j<claimBenefitList.get(i).getAttachments().size();j++){
                        String formId = claimBenefitList.get(i).getAttachments().get(j).getFormId().toUpperCase();
                        String benCode = claimBenefitList.get(i).getBenCode().toUpperCase();
                        if(Commons.isNullOrEmpty(benCode)){
                            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject,Constants.benCode);
                            responseObject.setMessage(responseObject.getMessage() + "benCode");
                            return responseObject;
                        }
                        if (Commons.isNullOrEmpty(formId)) {
                            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject,Constants.formId);
                            responseObject.setMessage(responseObject.getMessage() + "formId");
                            return responseObject;
                        }
                        String attachName = claimBenefitList.get(i).getAttachments().get(j).getName().toUpperCase();
                        if(Commons.isNullOrEmpty(attachName)){
                            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject,Constants.name);
                            responseObject.setMessage(responseObject.getMessage() + "name");
                            return responseObject;
                        }
                        if(Commons.isNullOrEmpty(claimBenefitList.get(i).getAttachments().get(j).getLocalParth())){
                            jsonObject.getLifeAssured().getClaimBenefits().get(i).getAttachments().get(j).setLocalParth("");
                        }
                    }
                }
            }
        }
//        validate own
        if(Commons.isNullOrEmpty(ownClientId)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.ownClientId);
            responseObject.setMessage(responseObject.getMessage() + "clientId");
            return responseObject;
        }
        if(Commons.isNullOrEmpty(ownClientNumber)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.ownClientNumber);
            responseObject.setMessage(responseObject.getMessage() + "clientNumber");
            return responseObject;
        }
        if(Commons.isNullOrEmpty(ownName)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.name);
            responseObject.setMessage(responseObject.getMessage() + "name");
            return responseObject;
        }
        if(Commons.isNullOrEmpty(ownCitizenId)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.ownCitizenId);
            responseObject.setMessage(responseObject.getMessage() + "citizenId");
            return responseObject;
        }
//        validate paymentInfo
        if(Commons.isNullOrEmpty(paymenInfoMode)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.paymentInfoMode);
            responseObject.setMessage(responseObject.getMessage() + "mode");
            return responseObject;
        }
        // validate receiverCidIssueDate
        if(!Commons.isDateCorrect(receiverCidIssueDate)){
            ResponseObject responseObject = Utils.responseValidateJsonObjectInputData(jsonObject, Constants.receiverCidIssueDate);
            responseObject.setMessage(responseObject.getMessage() + "receiverCidIssueDate");
            return responseObject;
        }
        return null;
    }

    @Override
    public ResponseObject validateJsonObjectFollowUp(JsonObjectFollowup jsonObjectFollowup) {
        logger.info("ClaimRequestId: " + jsonObjectFollowup.getClaimRequestId());
        String claimRequesId = jsonObjectFollowup.getClaimRequestId().toUpperCase();
        String claimId = jsonObjectFollowup.getClaimId().toUpperCase();
        if (Commons.isNullOrEmpty(claimRequesId)) {
            ResponseObject responseObject = Utils.responseValidateJsonObjectFolowUpInputData(Constants.claimRequestId);
            responseObject.setMessage(responseObject.getMessage() + "claimRequestId");
            return responseObject;
        }
        if (Commons.isNullOrEmpty(claimId)) {
            ResponseObject responseObject = Utils.responseValidateJsonObjectFolowUpInputData(Constants.claimRequestId);
            responseObject.setMessage(responseObject.getMessage() + "claimRequestId");
            return responseObject;
        }
        if(jsonObjectFollowup.getAttachments().size() < 1){
            ResponseObject responseObject = Utils.responseValidateJsonObjectAttach(Constants.attachments);
            responseObject.setMessage(responseObject.getMessage() + "attachments có ít nhất 1 file");
            return responseObject;
        }
        if (jsonObjectFollowup.getAttachments().size() > 0) {
            for (int i = 0; i < jsonObjectFollowup.getAttachments().size(); i++) {
                String formId = jsonObjectFollowup.getAttachments().get(i).getFormId().toUpperCase();
                if (Commons.isNullOrEmpty(formId)) {
                    ResponseObject responseObject = Utils.responseValidateJsonObjectFolowUpInputData(Constants.formId);
                    responseObject.setMessage(responseObject.getMessage() + "formId");
                    return responseObject;
                }
                String name = jsonObjectFollowup.getAttachments().get(i).getName().toUpperCase();
                if (Commons.isNullOrEmpty(name)) {
                    ResponseObject responseObject = Utils.responseValidateJsonObjectFolowUpInputData(Constants.name);
                    responseObject.setMessage(responseObject.getMessage() + "name");
                    return responseObject;
                }
                String followUpCode = jsonObjectFollowup.getAttachments().get(i).getFollowupCode().toUpperCase();
                if (Commons.isNullOrEmpty(followUpCode)) {
                    ResponseObject responseObject = Utils.responseValidateJsonObjectFolowUpInputData(Constants.followUpCode);
                    responseObject.setMessage(responseObject.getMessage() + "followUpCode");
                    return responseObject;
                }
                if (Commons.isNullOrEmpty(jsonObjectFollowup.getAttachments().get(i).getLocalPath())) {
                    jsonObjectFollowup.getAttachments().get(i).setLocalPath("");
                }
            }
        }

        return null;
    }

}
