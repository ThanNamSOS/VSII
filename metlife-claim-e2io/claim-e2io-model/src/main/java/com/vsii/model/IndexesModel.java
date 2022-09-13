package com.vsii.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "d2ckyc")
public class IndexesModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String applicationNo;
    private String channel;
    private String companyCode;
    private String branchCode;
    private String insuredName;
    private String insuredIdType;
    private String insuredIdNo;
    private String ownerName;
    private String ownerIdType;
    private String ownerIdNo;
    private String productCode;
    private String region;
    private String agentCode;
    private String submissionDate;
    private String nationality;
    private String occupation;
    private String mobilePhone;
    private String email;
    private String contactAddress1;
    private String contactAddress2;
    private String contactAddress3;
    private String contactAddress4;
    private String sumAssured;
    private String policyTerm;
    private String paymentFreq;
    private String ownerDOB;
    private String regularPremium;
    private String regularBasePremium;
    private String regularTopUpPremium;
    private String saleSubmissonDate;
    private String contestCode;
    private String submissionName;
    private String submissionType;
    private List<DocumentModel> documentModels;
    private String mode;
}
