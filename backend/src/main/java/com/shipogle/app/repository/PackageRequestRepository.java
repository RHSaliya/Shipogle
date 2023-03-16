package com.shipogle.app.repository;

import com.shipogle.app.model.PackageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRequestRepository extends JpaRepository<PackageRequest,Integer> {
    PackageRequest getPackageRequestById(Integer package_id);
//    List<PackageRequest> getAllPackageRequestBy_package_IdAndDeliverer_Id(Integer id,Integer id);
    int countAllBy_package_Id(Integer id);
    int countAllBy_package_IdAndDeliverer_Id(@Param("_package_Id") Integer package_id,@Param("Deliverer_Id") Integer deliverer_id);
}
