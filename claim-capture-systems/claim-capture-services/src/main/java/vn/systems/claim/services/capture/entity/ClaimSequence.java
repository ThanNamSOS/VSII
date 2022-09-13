package vn.systems.claim.services.capture.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "IWS_CLAIM_SEQUENCE", schema = "IWS")
@Entity
@Data
public class ClaimSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CLAIMREQUESTID")
    private String claimRequestId;

    @Column(name = "CURRENTCLAIMID")
    private int currentClaimId;


    @Column(name = "CREATED_DATE")
    @CreationTimestamp
    private Timestamp createDate;


    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private Timestamp updateDate;

}
