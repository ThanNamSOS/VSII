package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_ADDITIONAL_BENEFIT_ATT",schema = "IWS")
public class ClaimAdditionalBenefitAttEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_CLAIM_ADDITONAL_BEN")
    private Integer claimAdditional;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "FILETYPE")
    private String fileType;

    @Column(name = "FORMID")
    private String formId;

    @Column(name = "FOLLOWUPCODE")
    private String followUpCode;
}
