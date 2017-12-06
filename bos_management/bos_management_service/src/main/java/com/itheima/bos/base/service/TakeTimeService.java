package com.itheima.bos.base.service;

import com.itheima.bos.domain.base.TakeTime;

import java.util.List;

public interface TakeTimeService {
    void save(TakeTime takeTime);

    List<TakeTime> pageQuery();
}
