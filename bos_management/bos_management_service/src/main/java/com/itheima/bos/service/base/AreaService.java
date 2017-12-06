package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Area;

/**
 * ClassName:AreaService <br/>
 * Function: <br/>
 * Date: Nov 30, 2017 8:59:45 PM <br/>
 */
@Service
@Transactional
public interface AreaService {

    void save(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

    List<Area> pageQuery();

    List<Area> findByQ(String q);
}
