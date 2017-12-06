package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Standard;


/**  
 * ClassName:StandardDao <br/>  
 * Function:  <br/>  
 * Date:     Nov 28, 2017 9:17:55 PM <br/>       
 */
//JpaRepository<操作的具体实体类, 该类的主键的类>
public interface StandardRepository extends JpaRepository<Standard, Long> {
    
    
    
}
  
