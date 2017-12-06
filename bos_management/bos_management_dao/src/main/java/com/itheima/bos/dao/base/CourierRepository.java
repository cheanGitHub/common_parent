package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

import java.util.List;


/**
 * ClassName:StandardDao <br/>
 * Function:  <br/>
 * Date:     Nov 28, 2017 9:17:55 PM <br/>
 */
//JpaRepository<操作的具体实体类, 该类的主键的类>
//JpaSpecificationExecutor用于实现Criteria查询
//JpaSpecificationExecutor不可单独使用,必须和JpaRepository一起使用
public interface CourierRepository extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

    List<Courier> findByDeltagIsNull();

    @Modifying
    @Query("update Courier set deltag = 1 where id = ?1")
    void updateById(Long id);
}
  
