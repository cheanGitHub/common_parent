package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.*;
import com.itheima.bos.domain.base.*;
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

        fixedArea.getCouriers().add(courier);
    }

    @Autowired
    private SubAreaRepository subAreaRepository;

    private List<Long> subAreaIds;

    public void setSubAreaIds(List<Long> subAreaIds) {
        this.subAreaIds = subAreaIds;
    }

    @Override
    public void unAssignSubAreas2FixedArea(Long id) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        List<SubArea> subAreas = subAreaRepository.findByFixedArea(fixedArea);
        for (SubArea subArea : subAreas) {
            subArea.setFixedArea(null);
        }

    }

    @Override
    public void assignSubAreas2FixedArea(Long id, List<Long> subAreaIds) {
        List<SubArea> subAreas = subAreaRepository.findAll(subAreaIds);
        for (SubArea subArea : subAreas) {
            FixedArea fixedArea = fixedAreaRepository.findOne(id);
            subArea.setFixedArea(fixedArea);
        }
    }
}