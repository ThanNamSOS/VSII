package com.vsii.api;

import com.vsii.entity.ClaimAdditionalEntity;
import com.vsii.entity.ClaimRequestEntity;
import com.vsii.entity.IwsCaseHistoryEntity;
import com.vsii.enums.SourceEnum;
import com.vsii.model.ClaimModel;
import com.vsii.model.JsonObject;
import com.vsii.model.JsonObjectFollowup;
import com.vsii.service.*;
import com.vsii.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class maintestApi {
    @Autowired
    ClaimRequestService claimRequestService;

    @Autowired
    ClaimBenefitInfoService claimBenefitInfoService;

    @Autowired
    ClaimCaseService claimCaseService;

    @Autowired
    ClaimAdditionalService claimAdditionalService;

    @Autowired
    ClaimAdditionalBenefitService additionalBenefitService;

    @Autowired
    ClaimAdditionalBenefitAttService claimAdditionalBenefitAttService;

    @Autowired
    ClaimBenefitService claimBenefitService;

    @Autowired
    ClaimBenefitAttService claimBenefitAttService;

    @PostMapping("demo")
    public void save(@RequestBody JsonObject jsonObject) {
        claimRequestService.save(jsonObject);
        ClaimRequestEntity claimRequestEntity = claimRequestService.save(jsonObject);
        ClaimModel claimModel = new ClaimModel();
        String claimRequestId = "O00008360";
        claimModel.setClaimRequestId(claimRequestId);
        claimModel.setClaimId(claimRequestId);
        claimModel.setSource("PPX");
        claimModel.setBenCode("BEN01");
        if (claimRequestEntity != null) {
           // claimBenefitInfoService.Save(jsonObject, claimRequestEntity.getId());
            claimCaseService.Save(jsonObject, 11111, claimModel);
        }
//        List<Integer> idClaimBenefit = claimBenefitService.Save(jsonObject);
//        if (idClaimBenefit != null) {
//            for (Integer id : idClaimBenefit) {
//                claimBenefitAttService.Save(jsonObject, id);
//            }
//        }
    }

    @PostMapping("demoAdd")
    public void saveData(@RequestBody JsonObjectFollowup objectFollowup) {
        ClaimAdditionalEntity claimAdditional = claimAdditionalService.Save(objectFollowup, null);
        if (claimAdditional != null) {
            List<Integer> idAddBens = additionalBenefitService.Save(objectFollowup, claimAdditional.getId());
            if (idAddBens.size() > 0) {
                for (Integer idAddBen : idAddBens) {
                    claimAdditionalBenefitAttService.Save(objectFollowup, idAddBen);
                }
            }
        }
    }


    @Autowired
    IwsCaseHistoryService iwsCaseHistoryService;


    @PostMapping("demoHIS")
    public void saveData(@RequestBody JsonObject jsonObject) {
        List<IwsCaseHistoryEntity> lst = new ArrayList<>();
        IwsCaseHistoryEntity entity = new IwsCaseHistoryEntity();
        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());
        entity.setAppNo("O999O001");
        entity.setCaseStatus("Completed Normal");
        entity.setResponse("New Claim");
        entity.setCreatedDate(timestamp);
        entity.setCompletedTime(timestamp);
        entity.setReceivedTime(timestamp);
        entity.setSubmissionDate(timestamp);
        entity.setUserId("ecm.uat.ce.user");
        lst.add(entity);
        IwsCaseHistoryEntity saleEntity = new IwsCaseHistoryEntity();
        Timestamp submissionDate = DateUtils.convertTimestamp(jsonObject.getSubmissionTimestamp());
        saleEntity.setAppNo("O999O001");
        saleEntity.setCaseStatus("Completed Normal");
        saleEntity.setCreatedDate(timestamp);
        saleEntity.setCompletedTime(submissionDate);
        saleEntity.setReceivedTime(submissionDate);
        saleEntity.setSubmissionDate(submissionDate);
        if (jsonObject.getSource().equals(SourceEnum.SCAN.toString())) {
            saleEntity.setResponse("Scan Submitted");
            saleEntity.setUserId("Scan");
        } else {
            saleEntity.setResponse("Sales Submmited");
            saleEntity.setUserId("Sales");
        }
        lst.add(saleEntity);
        iwsCaseHistoryService.save(lst);
    }

    public static void main(String[] args) {
        Timestamp timestamp = DateUtils.convertTimestamp("2022-05-27T15:25:30.445+07:00");
        System.out.println(timestamp.getTime());
    }
}
