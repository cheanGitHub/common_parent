package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SubAreaService {

    Page<SubArea> pageQuery(Pageable pageable);

    List<SubArea> pageQuery();

    void save(SubArea model);

    List<SubArea> findByFixedAreaIsNull();

    List<SubArea> findByFixedArea(Long id);
}
