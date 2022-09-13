package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Table(name = "IWS_CLAIM_BENEFIT_INFO",schema = "IWS")
@Entity
public class ClaimBenefitInfoEntity {
    private static final long serialVersionUID = -2543425088717298236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name="ID_CLAIM_REQUEST")
    private Integer idClaimRequest;

    @Column(name = "BENCODE")
    private String benCode;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "DIAGNOSTICCODE")
    private String diagnosticCode;

    @Column(name = "DIAGNOSTICDESCRIPTION")
    private String diagnosticDescripTion;

}
