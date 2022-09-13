package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_PENDING_CODE",schema = "IWS")
public class ClaimPendingCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PENDINGCODE")
    private String penDingCode;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CREATED_DATE")
    private Timestamp createDate;

    @Column(name = "UPDATED_DATE")
    private Timestamp updateDate;
}
