package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.FixedArea;
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

    @Override
    public List<SubArea> findByFixedAreaIsNull() {
        return subAreaRepository.findByFixedAreaIsNull();
    }

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public List<SubArea> findByFixedArea(Long id) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        return subAreaRepository.findByFixedArea(fixedArea);
    }
}
