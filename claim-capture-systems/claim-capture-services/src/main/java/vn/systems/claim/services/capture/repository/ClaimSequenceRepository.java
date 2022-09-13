package vn.systems.claim.services.capture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.systems.claim.services.capture.entity.ClaimSequence;

import java.util.List;

@Repository
public interface ClaimSequenceRepository extends JpaRepository<ClaimSequence, Integer> {
    @Query(value = "select c from ClaimSequence c where c.claimRequestId =:requestId order by c.claimRequestId desc")
    List<ClaimSequence> getClaimSequence(String requestId);
}
