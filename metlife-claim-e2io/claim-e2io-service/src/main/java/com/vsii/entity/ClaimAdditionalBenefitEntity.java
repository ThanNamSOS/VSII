package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_ADDITIONAL_BENEFIT",schema = "IWS")
public class ClaimAdditionalBenefitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_CLAIM_ADDITONAL")
    private Integer idClaimAdditional;

    @Column(name = "BENCODE")
    private String benCode;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "DIAGNOSTICCODE")
    private String diagnosticCode;

    @Column(name = "DIAGNOSTICDESCRIPTION")
    private String diagnosticDescription;

}
