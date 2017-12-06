package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public List<FixedArea> pageQuery() {
        return fixedAreaRepository.findAll();
    }

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void associationCourierToFixedArea(Long courier_FixedAreaId, Long courierId, Long takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(courier_FixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);

        courier.setTakeTime(takeTime);
        //courierRepository.save(courier);

        fixedArea.getCouriers().add(courier);
        //fixedAreaRepository.save(fixedArea);
    }
}