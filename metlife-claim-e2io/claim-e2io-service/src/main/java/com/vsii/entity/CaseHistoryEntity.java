package com.vsii.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "IWS_CASE_HISTORY",schema = "IWS")
public class CaseHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "APP_NO")
    private String appNo;


    @Column(name = "POLICY_NO")
    private String policyNo;

    @Column(name = "RECEIVED_TIME")
    private Timestamp receivedTime;

    @Column(name = "COMPLETED_TIME")
    private Timestamp completedTime;

    @Column(name = "QUEUE_NAME")
    private Timestamp queueName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "SUBMISSION_DATE")
    private Timestamp submissionDate;

    @Column(name = "CREATED_DATE")
    private Timestamp createDate;

    @Column(name = "RECEIVER")
    private String receiver;
}
