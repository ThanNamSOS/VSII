package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_BENEFIT",schema = "IWS")
@NamedQueries(value = {
        @NamedQuery(name = "ClaimBenefitEntity.findByBencode",
                query = "SELECT ben From ClaimBenefitEntity ben where ben.benefitCode = :bencode")

})
public class ClaimBenefitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "BENEFIT_CODE")
    private String benefitCode;

    @Column(name = "BENEFIT_NAME")
    private String benefitName;

    @Column(name = "CREATED_DATE")
    private Timestamp creatDate;

    @Column(name = "UPDATED_DATE")
    private Timestamp updateDate;

}
