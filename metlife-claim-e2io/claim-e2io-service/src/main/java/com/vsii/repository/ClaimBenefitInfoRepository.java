package com.vsii.repository;

import com.vsii.entity.ClaimBenefitInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimBenefitInfoRepository extends JpaRepository<ClaimBenefitInfoEntity,Integer> {
}
