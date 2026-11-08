package com.srm.inventory.repository;
import com.srm.inventory.entity.Product; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface ProductRepository extends JpaRepository<Product,Long> { List<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name,String sku); boolean existsBySkuAndIdNot(String sku,Long id); List<Product> findByStockQuantityLessThanEqual(Integer level); }
