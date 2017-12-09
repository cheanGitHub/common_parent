package com.itheima.crm.dao;

import com.itheima.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?1")
    void unAssignCustomers2FixedArea(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = ?1 where id = ?2")
    void assignCustomers2FixedArea(String fixedAreaId, Long customerId);

    Customer findByTelephone(String telephone);
}
