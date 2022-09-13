package com.vsii.repository;

import com.vsii.entity.ClaimAdditionalBenefitEntity;
import com.vsii.entity.ClaimAdditionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimAdditinalBenefitRepository extends JpaRepository<ClaimAdditionalBenefitEntity, Integer> {
}
