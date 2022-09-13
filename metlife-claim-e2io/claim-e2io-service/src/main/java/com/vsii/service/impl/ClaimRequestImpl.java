package com.vsii.service.impl;

import com.vsii.entity.ClaimRequestEntity;
import com.vsii.model.*;
import com.vsii.repository.ClaimBenefitInfoRepository;
import com.vsii.repository.ClaimRequestRepository;
import com.vsii.service.ClaimCaseService;
import com.vsii.service.ClaimRequestService;
import com.vsii.utils.DateUtils;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ClaimRequestImpl implements ClaimRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimRequestImpl.class);
    @Autowired
    private ClaimRequestRepository requestRepository;

    @Autowired
    private ClaimCaseService claimCaseService;
    @Autowired
    private ClaimBenefitInfoServiceImpl claimBenefitInfoService;

    @Override
    public void save(JsonObject jsonObject) {
        LOGGER.info("Save Claim Request Start");
        Fatca fatca = jsonObject.getFatca();
        LifeAssured lifeAssured = jsonObject.getLifeAssured();
        PaymentInfo paymentInfo = jsonObject.getPaymentInfo();
        Owner owner = jsonObject.getOwner();
        ClaimRequestEntity claimEntity = new ClaimRequestEntity();

        claimEntity.setPolicyNo(jsonObject.getPolicyNumber());
        claimEntity.setSource(jsonObject.getSource());
        if(!jsonObject.getSubmissionTimestamp().equals("") && jsonObject.getSubmissionTimestamp() != null ) {
            claimEntity.setSubmissionTimestamp(DateUtils.convertTimestamp(jsonObject.getSubmissionTimestamp()));
        }
        if(fatca != null){
            claimEntity.setFatcaCode(fatca.getCode());
            claimEntity.setFatcaDescription(fatca.getDescription());
        }
        if(lifeAssured != null){
            claimEntity.setLaClientId(lifeAssured.getClientId());
            claimEntity.setLaCitizenId(lifeAssured.getCitizenId());
            claimEntity.setLaClientNumber(lifeAssured.getClientNumber());
            claimEntity.setLaLifeNo(lifeAssured.getLifeNo());
            claimEntity.setLaName(lifeAssured.getName());
        }
        if(owner != null){
            claimEntity.setOwnerClientNumber(owner.getClientNumber());
            claimEntity.setOwnerClientId(owner.getClientId());
            claimEntity.setOwnerName(owner.getName());
            claimEntity.setOwnerCitizenId(owner.getCitizenId());
        }
        if(paymentInfo != null){
            claimEntity.setPayMode(paymentInfo.getMode());
            claimEntity.setPayAccNumber(paymentInfo.getAccountNumber());
            claimEntity.setPayBank(paymentInfo.getBank());
            claimEntity.setPayPolicyNumber(paymentInfo.getPolicyNumber());
            claimEntity.setPayAccOwnerName(paymentInfo.getAccountOwnerName());
            claimEntity.setPayReceiverName(paymentInfo.getReceiverName());
            claimEntity.setPayReceiverCityzenId(paymentInfo.getReceiverCitizenId());
            claimEntity.setPayReveiverCidIssueDate(DateUtils.convertDate(paymentInfo.getReceiverCidIssueDate()));
            claimEntity.setPAYRECEIVECIDISSUEPLACE(paymentInfo.getReceiverCidIssuePlace());
            claimEntity.setPayReceiveBank(paymentInfo.getReceiverBank());
        }
        requestRepository.save(claimEntity);
        LOGGER.info("Save Claim Request successful");
//        return  claimEntity.getId();
    }



}
