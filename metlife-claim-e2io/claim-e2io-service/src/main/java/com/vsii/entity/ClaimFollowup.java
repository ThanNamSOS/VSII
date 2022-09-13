package com.vsii.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "IWS_CLAIM_FOLLOWUP_CODE", schema = "IWS")
@Entity
@Data
public class ClaimFollowup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FOLLOWUPCODE")
    private String followupCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLE")
    private int enable;

    @Column(name = "CREATED_DATE")
    @CreationTimestamp
    private Timestamp createDate;


    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private Timestamp updateDate;

}
