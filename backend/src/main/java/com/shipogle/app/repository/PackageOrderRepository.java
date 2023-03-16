package com.shipogle.app.repository;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.PackageOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder,Integer> {
    PackageOrder getPackageOrderBy_package(Package _package);
    PackageOrder getBy_package_Id(Integer package_id);
}