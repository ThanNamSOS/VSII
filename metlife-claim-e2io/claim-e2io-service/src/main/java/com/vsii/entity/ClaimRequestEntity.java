package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_REQUEST",schema = "IWS")
public class ClaimRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "OWNERCLIENTID")
    private String ownerClientId;

    @Column(name = "OWNERNAME")
    private String ownerName;

    @Column(name = "POLICYNO")
    private String policyNo;

    @Column(name = "SUBMISSIONTIMESTAMP")
    private Timestamp submissionTimestamp;

    @Column(name = "OWNERCLIENTNUMBER")
    private String ownerClientNumber;

    @Column(name = "OWNERCITIZENID")
    private String ownerCitizenId;

    @Column(name = "LACLIENTID")
    private String laClientId;

    @Column(name = "LACLIENTNUMBER")
    private String laClientNumber;

    @Column(name = "LALIFENO")
    private String laLifeNo;

    @Column(name = "LANAME")
    private String laName;

    @Column(name = "LACITIZENID")
    private String laCitizenId;

    @Column(name = "PAYMODE")
    private String payMode;

    @Column(name = "PAYPOLICYNUMBER")
    private String payPolicyNumber;

    @Column(name = "PAYACCOWNERNAME")
    private String payAccOwnerName;

    @Column(name = "PAYBANK")
    private String payBank;

    @Column(name = "PAYRECEIVERNAME")
    private String payReceiverName;

    @Column(name = "PAYACCNUMBER")
    private String payAccNumber;

    @Column(name = "PAYRECEIVERCITYZENID")
    private String payReceiverCityzenId;

    @Column(name = "PAYREVEIVERCIDISSUEDATE")
    private Timestamp payReveiverCidIssueDate;

    @Column(name = "PAYRECEIVECIDISSUEPLACE")
    private String  payReceiverCidIssuePlace;

    @Column(name = "PAYRECEIVEBANK")
    private String payReceiveBank;

    @Column(name = "FATCACODE")
    private String fatcaCode;

    @Column(name = "FATCADESCRIPTION")
    private String fatcaDescription;

}
