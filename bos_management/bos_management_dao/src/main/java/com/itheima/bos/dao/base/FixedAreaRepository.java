package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FixedAreaRepository extends JpaRepository<FixedArea, Long> {


}
