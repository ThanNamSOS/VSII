package com.vsii.repository;

import com.vsii.entity.IwsCaseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IwsCaseHistoryRepository  extends JpaRepository<IwsCaseHistoryEntity, Integer> {
}
