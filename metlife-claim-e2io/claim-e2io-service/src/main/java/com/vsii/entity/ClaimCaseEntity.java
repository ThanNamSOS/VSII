package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_CASE", schema = "IWS")
public class ClaimCaseEntity {
    private static final long serialVersionUID = -2543425088717298236L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "ID_CLAIM_REQUEST")
    private Integer idClaimRequest;

    @Column(name = "CLAIMID")
    private String claimID;

    @Column(name = "AGENTCODE")
    private String agentCode;

    @Column(name = "BRANCHCODE")
    private String branchCode;

    @Column(name = "BRANCHNAME")
    private String branchName;

    @Column(name = "COMPANYCODE")
    private String companyCode;

    @Column(name = "CONTESTCODE")
    private String ContestCode;

    @Column(name = "EXPIREDDATE")
    private String ExpiredDate;

    @Column(name = "NOTE")
    private String Note;

    @Column(name = "PROCESSINGSTATUS")
    private String ProcessingStatus;

    @Column(name = "PRODUCTCODE")
    private String ProductCode;

    @Column(name = "REGION")
    private String Region;

    @Column(name = "SUBMISSIONDATE")
    private String submissionDate;

    @Column(name = "SUBMISSIONNAME")
    private String SubmissionName;

    @Column(name = "SUBMISSIONTYPE")
    private String SubmissionType;

    @Column(name = "CLAIMREQUESTID")
    private String ClaimRequestId;

    @Column(name = "CLAIMREQUESTFROM")
    private String ClaimRequestFrom;

    @Column(name = "BENCODE")
    private String benCode;

    @Column(name = "BENSTARTDATE")
    private Timestamp benStartDate;


    @Column(name = "BENENDDATE")
    private Timestamp benEndDate;

    @Column(name = "ICDCODE")
    private String icdCode;

    @Column(name = "ICDDESCRIPTION")
    private String icdDescription;

    @Column(name = "PAYBANKBRANCH")
    private String payBankBranch;

    @Column(name = "PAYRECEIVEBANKBRANCH")
    private String payReceiveBankBranch;

    @Column(name = "INVESTIGATENOTE")
    private String InvestigateNote;

    @Column(name = "REAPPRAIENOTE")
    private String ReappraieNote;

    @Column(name = "TPARESULT")
    private String TPAResult;

    @Column(name = "TPARESULTCLAIMVALUE")
    private String TPAResultClaimValue;

    @Column(name = "DECISIONNOTE")
    private String decisionNote;

    @Column(name = "DECISION")
    private String decision;

    @Column(name = "DECISIONDATE")
    private Timestamp decisionDate;

    @Column(name = "DECLINECODE")
    private Timestamp decisionCode;

    @Column(name = "DECLINEREASON")
    private String declineReason;

    @Column(name = "COMPONENT")
    private String component;

    @Column(name = "INCURDATE")
    private Timestamp incurDate;

    @Column(name = "DISCHARDATE")
    private Timestamp discharDate;

    @Column(name = "CURRENTUSER")
    private String currentUser;

    @Column(name = "CURRENTQUEUE")
    private String currentQueue;

    @Column(name = "ASSESSOR")
    private String assessor;

    @Column(name = "PREVIOUSQUEUE")
    private String previousQueue;

    @Column(name = "PREVIOUSUSER")
    private String previousUser;

    @Column(name = "TPAPARTNER")
    private String tpaPartner;

    @Column(name = "OEAMOUNT")
    private Double oeaMount;


    @Column(name = "IPDAYS")
    private Timestamp ipDays;

    @Column(name = "IPAMOUNTPERDAY")
    private Double ipAmountPerDay;

    @Column(name = "IPAMOUNT")
    private Double ipAmount;

    @Column(name = "SUDESC")
    private String suDESC;

    @Column(name = "SUAMOUNT")
    private String suAmount;

    @Column(name = "TOTALAMOUNT")
    private Double totalAmount;

}
