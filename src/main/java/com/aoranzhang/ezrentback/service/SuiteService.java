package com.aoranzhang.ezrentback.service;

import com.aoranzhang.ezrentback.data.entity.Suite;
import com.aoranzhang.ezrentback.data.repository.SuiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuiteService {
  @Autowired
  private SuiteRepository suiteRepository;

  @Transactional
  public void saveSuite(Suite suite) {
    Suite existing = suiteRepository.findOne(suite.getId());
    if(existing != null) {
      existing.copy(suite);
      suiteRepository.save(existing);
    }else {
      suiteRepository.save(suite);
    }
  }

  public Suite findSuite(String id) {
    return suiteRepository.findOne(id);
  }
}
