package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);

    List<FixedArea> pageQuery();

    void associationCourierToFixedArea(Long courier_FixedAreaId, Long courierId, Long takeTimeId);

    void unAssignSubAreas2FixedArea(Long id);

    void assignSubAreas2FixedArea(Long id, List<Long> subAreaIds);
}
