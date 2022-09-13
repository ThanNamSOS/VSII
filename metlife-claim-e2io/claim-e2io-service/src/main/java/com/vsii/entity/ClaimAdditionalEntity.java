package com.vsii.entity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_ADDITIONAL",schema = "IWS")
public class ClaimAdditionalEntity {
    private static final long serialVersionUID = -2543425088717298236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CLAIMREQUESTID")
    private String claimRequestId;

    @Column(name = "CLAIMID")
    private String claimId;

    @Column(name = "CREATED_DATE")
    private Timestamp createDate;

    @Column(name = "OWNERCLIENTID")
    private String ownerClientId;

    @Column(name = "OWNERCLIENTNUMBER")
    private String ownerClientNumber;

    @Column(name = "OWNERNAME")
    private String ownerName;

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

    @Column(name = "FATCACODE")
    private String fatcaCode;

    @Column(name = "FATCADESCRIPTION")
    private String fatcaDescription;
}
