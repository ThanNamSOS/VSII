package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CLAIM_PENDING_DOCUMENT",schema = "IWS")
public class ClaimPendingDocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CLAIM_ID")
    private String claimId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PENDING_CODE")
    private String pendingCode;

    @Column(name = "PENDING_REMARK")
    private String pendingRemark;

    @Column(name = "DOCUMENT_STATUS")
    private Integer documentStatus;

    @Column(name = "REQUEST_DATE")
    private Timestamp requestDate;

    @Column(name = "EXPIRED_DATE")
    private Timestamp expiredDate;

    @Column(name = "RECIEVED_DATE")
    private Timestamp recievedDate;

    @Column(name = "CREATED_DATE")
    private Timestamp createDate;

    @Column(name = "UPDATED_DATE")
    private Timestamp updateDate;

    @Column(name = "UPDATED_USER")
    private Integer updateUser;

    @Column(name = "POLNO")
    private String polNo;
}
