package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_INVESTIGATE",schema = "IWS")
public class ClaimInvestigate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CLAIMREQUESTID")
    private String claimRequestID;

    @Column(name = "CLAIMID")
    private String claimID;

    @Column(name = "CREATED_DATE")
    private Timestamp createDate;

    @Column(name = "REMARK")
    private String remark;

}
