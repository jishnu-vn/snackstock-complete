package com.srm.inventory.repository;
import com.srm.inventory.entity.*; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> { @EntityGraph(attributePaths={"items","items.product"}) List<CustomerOrder> findByStatus(OrderStatus status); @Override @EntityGraph(attributePaths={"items","items.product"}) List<CustomerOrder> findAll(); }
