package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CASE_HISTORY", schema = "IWS")
public class IwsCaseHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "APP_NO")
    private String appNo;

    @Column(name = "CASE_STATUS")
    private String caseStatus;

    @Column(name = "COMPLETED_TIME")
    private Timestamp completedTime;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "POLICY_NO")
    private String policyNo;

    @Column(name = "QUEUE_NAME")
    private String queueName;

    @Column(name = "RECEIVED_TIME")
    private Timestamp receivedTime;

    private String response;

    @Column(name = "SUBMISSION_DATE")
    private Timestamp submissionDate;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "RECEIVER")
    private String receiver;


}
