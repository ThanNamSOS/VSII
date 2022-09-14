package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IWS_CLAIM_BENEFIT_ATT",schema = "IWS")
public class ClaimBenefitAttEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "ID_CLAIM_BEN")
    private Integer idClaimBen;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "FILETYPE")
    private String fileType;

    @Column(name = "FORMID")
    private String formId;

}
