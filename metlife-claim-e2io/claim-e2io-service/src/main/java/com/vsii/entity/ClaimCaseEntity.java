package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IWS_CLAIM_CASE",schema = "IWS")
public class ClaimCaseEntity {
    private static final long serialVersionUID = -2543425088717298236L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @JoinColumn(name="ID_CLAIM_REQUEST")
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

    @Column(name = "SUBMISSIONNAME")
    private String SubmissionName;

    @Column(name = "SUBMISSIONTYPE")
    private String SubmissionType;

    @Column(name = "CLAIMREQUESTID")
    private String ClaimRequestId;

    @Column(name = "CLAIMREQUESTFROM")
    private String ClaimRequestFrom;

    @Column(name = "INVESTIGATENOTE")
    private String InvestigateNote;

    @Column(name = "REAPPRAIENOTE")
    private String ReappraieNote;

    @Column(name = "TPARESULT")
    private String TPAResult;

    @Column(name = "TPARESULTCLAIMVALUE")
    private String TPAResultClaimValue;

    @Column(name = "CLAIMVALUE")
    private String ClaimValue;

    @Column(name = "CLAIMRESULT")
    private String ClaimResult;

    @Column(name = "JETCLAIMMANUAL")
    private String JetClaimManual;

    @Column(name = "NOTEREJECT")
    private String NoteReject;

}
