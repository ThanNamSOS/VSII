package com.vsii.repository;

import com.vsii.entity.ClaimBenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimBenefitRepository extends JpaRepository<ClaimBenefitEntity, Integer> {
    @Query(name = "ClaimBenefitEntity.findByBencode")
    public ClaimBenefitEntity findByBencode(@Param("bencode") String bencode);
}
