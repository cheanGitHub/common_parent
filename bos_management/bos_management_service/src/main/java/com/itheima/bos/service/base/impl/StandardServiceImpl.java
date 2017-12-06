package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

import java.util.List;

/**
 * ClassName:StandardServiceImpl <br/>
 * Function:  <br/>
 * Date:     Nov 28, 2017 8:56:20 PM <br/>
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public List<Standard> pageQuery() {
        return standardRepository.findAll();
    }

    @Override
    public Page<Standard> pageQuery(Pageable pageable) {

        return standardRepository.findAll(pageable);
    }

}
  
