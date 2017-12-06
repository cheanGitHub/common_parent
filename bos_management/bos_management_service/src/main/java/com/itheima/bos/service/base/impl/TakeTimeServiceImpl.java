package com.itheima.bos.service.base.impl;

import com.itheima.bos.base.service.TakeTimeService;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.TakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(TakeTime takeTime) {
        takeTimeRepository.save(takeTime);
    }

    @Override
    public List<TakeTime> pageQuery() {
        return takeTimeRepository.findAll();
    }
}
