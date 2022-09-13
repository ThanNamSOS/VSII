package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_REUW",schema = "IWS")
public class ClaimReuw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CLAIMREQUESTID")
    private String claimRequestId;

    @Column(name = "CLAIMID")
    private String claimId;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "REMARK")
    private String remark;

}
