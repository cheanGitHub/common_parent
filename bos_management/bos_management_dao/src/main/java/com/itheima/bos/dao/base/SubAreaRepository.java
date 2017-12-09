package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubAreaRepository extends JpaRepository<SubArea, Long> {

    List<SubArea> findByFixedAreaIsNull();

    @Query("from SubArea where fixedArea = ?1")
    List<SubArea> findByFixedArea(FixedArea fixedArea);
}
