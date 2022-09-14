package com.vsii.service.impl;

import com.vsii.entity.IwsCaseHistoryEntity;
import com.vsii.repository.IwsCaseHistoryRepository;
import com.vsii.service.IwsCaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IwsCaseHistoryServiceImpl implements  IwsCaseHistoryService{
    @Autowired
    private IwsCaseHistoryRepository repo;

    @Override
    public void save(List<IwsCaseHistoryEntity> entites) {
        repo.saveAll(entites);
    }
}
