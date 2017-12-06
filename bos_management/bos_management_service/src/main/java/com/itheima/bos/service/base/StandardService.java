package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Nov 28, 2017 8:54:46 PM <br/>       
 */
public interface StandardService {

    void save(Standard standard);

    Page<Standard> pageQuery(Pageable pageable);

    List<Standard> pageQuery();
}
  
