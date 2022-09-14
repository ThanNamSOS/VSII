package com.vsii.repository;

import com.vsii.entity.ClaimBenefitAttEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimBenefitAttRepository extends JpaRepository<ClaimBenefitAttEntity,Integer> {

}
