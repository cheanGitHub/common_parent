package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.jpa.domain.Specification;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Nov 28, 2017 8:54:46 PM <br/>       
 */
public interface CourierService {

    void save(Courier standard);

    List<Courier> pageQuery();

    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

    void bathDel(String[] ids);

    List<Courier> findByDeltagIsNull();
}
  
