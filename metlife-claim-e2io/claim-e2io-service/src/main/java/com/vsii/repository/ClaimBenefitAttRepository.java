package com.vsii.repository;

import com.vsii.entity.ClaimBenefitAtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimBenefitAttRepository extends JpaRepository<ClaimBenefitAtt,Integer> {

}
