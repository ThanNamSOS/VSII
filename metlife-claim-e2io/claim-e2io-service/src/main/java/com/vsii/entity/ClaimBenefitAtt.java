package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IWS_CLAIM_BENEFIT_ATT",schema = "IWS")
public class ClaimBenefitAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "ID_CLAIM_BEN")
    private Integer idClaimBen;

    @Column(name = "benefitName")
    private String benefitName;

    @Column(name = "FILETYPE")
    private String fileType;

}
