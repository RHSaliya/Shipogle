package com.shipogle.app.repository;

import com.shipogle.app.model.PackageRequest;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRequestRepository extends JpaRepository<PackageRequest,Integer> {
    PackageRequest getPackageRequestById(Integer package_request_id);
    List<PackageRequest> getAllBy_package_Id(Integer package_id);
    int countAllBy_package_IdAndDeliverer_Id(@Param("_package_Id") Integer package_id,@Param("Deliverer_Id") Integer deliverer_id);
    List<PackageRequest> getAllByDeliverer(User deliverer);
}
