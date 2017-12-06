package com.itheima.bos.service.base.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**
 * ClassName:StandardServiceImpl <br/>
 * Function: <br/>
 * Date: Nov 28, 2017 8:56:20 PM <br/>
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public List<Courier> pageQuery() {
        return courierRepository.findAll();
    }

    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void bathDel(String[] ids) {
        Set<Courier> couriers = new HashSet<>();
        for (String id : ids) {
            courierRepository.updateById(Long.decode(id));
        }

    }

    @Override
    public List<Courier> findByDeltagIsNull() {
        return courierRepository.findByDeltagIsNull();
    }
}
