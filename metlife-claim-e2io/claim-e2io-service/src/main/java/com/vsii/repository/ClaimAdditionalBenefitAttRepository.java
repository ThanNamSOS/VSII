package com.vsii.repository;

import com.vsii.entity.ClaimAdditionalBenefitAttEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimAdditionalBenefitAttRepository extends JpaRepository<ClaimAdditionalBenefitAttEntity,Integer> {
}
