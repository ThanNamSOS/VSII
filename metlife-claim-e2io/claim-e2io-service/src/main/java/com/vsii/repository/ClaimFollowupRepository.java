package com.vsii.repository;

import com.vsii.entity.ClaimFollowup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimFollowupRepository extends JpaRepository<ClaimFollowup, String> {
    @Query(value = "select f from ClaimFollowup f where f.followupCode=:followupCode")
    List<ClaimFollowup> findAllByFollowupCode(String followupCode);
}
