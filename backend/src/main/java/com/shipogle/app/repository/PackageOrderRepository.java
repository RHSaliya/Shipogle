package com.shipogle.app.repository;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.PackageOrder;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder,Integer> {
    PackageOrder getPackageOrderById(Integer package_order_id);
    PackageOrder getBy_package_Id(Integer package_id);

    List<PackageOrder> getAllBySender(User user);
}