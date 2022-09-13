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
    private Integer id;

    @Column(name = "SOURCE")
    private String Source;

    @Column(name = "OWNERCLIENTID")
    private String OwnerClientId;

    @Column(name = "OWNERNAME")
    private String OwnerName;

    @Column(name = "POLICYNO")
    private String PolicyNo;

    @Column(name = "SUBMISSIONTIMESTAMP")
    private Timestamp SubmissionTimestamp;

    @Column(name = "OWNERCLIENTNUMBER")
    private String OwnerClientNumber;

    @Column(name = "OWNERCITIZENID")
    private String OwnerCitizenId;

    @Column(name = "LACLIENTID")
    private String LaClientId;

    @Column(name = "LACLIENTNUMBER")
    private String LaClientNumber;

    @Column(name = "LALIFENO")
    private String LaLifeNo;

    @Column(name = "LANAME")
    private String LaName;

    @Column(name = "LACITIZENID")
    private String LaCitizenId;

    @Column(name = "PAYMODE")
    private String PayMode;

    @Column(name = "PAYPOLICYNUMBER")
    private String PayPolicyNumber;

    @Column(name = "PAYACCOWNERNAME")
    private String PayAccOwnerName;

    @Column(name = "PAYBANK")
    private String PayBank;

    @Column(name = "PAYRECEIVERNAME")
    private String PayReceiverName;

    @Column(name = "PAYACCNUMBER")
    private String PayAccNumber;

    @Column(name = "PAYRECEIVERCITYZENID")
    private String PayReceiverCityzenId;

    @Column(name = "PAYREVEIVERCIDISSUEDATE")
    private Timestamp PayReveiverCidIssueDate;

    @Column(name = "PAYRECEIVECIDISSUEPLACE")
    private String PAYRECEIVECIDISSUEPLACE;

    @Column(name = "PAYRECEIVEBANK")
    private String PayReceiveBank;

    @Column(name = "FATCACODE")
    private String FatcaCode;

    @Column(name = "FATCADESCRIPTION")
    private String FatcaDescription;

}
