package com.vsii.repository;

import com.vsii.entity.ClaimRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequestEntity, Integer> {
}
