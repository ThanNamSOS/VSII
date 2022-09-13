package com.vsii.repository;

import com.vsii.entity.ClaimCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimCaseRepository extends JpaRepository<ClaimCaseEntity, Integer> {
}
