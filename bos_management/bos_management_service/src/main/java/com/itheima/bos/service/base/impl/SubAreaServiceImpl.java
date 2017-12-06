package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Override
    public Page<SubArea> pageQuery(Pageable pageable) {
        return subAreaRepository.findAll(pageable);
    }

    @Override
    public List<SubArea> pageQuery() {
        return subAreaRepository.findAll();
    }

    @Override
    public void save(SubArea model) {
        subAreaRepository.save(model);
    }
}
