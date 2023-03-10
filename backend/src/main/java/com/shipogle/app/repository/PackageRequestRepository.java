package com.shipogle.app.repository;

import com.shipogle.app.model.PackageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRequestRepository extends JpaRepository<PackageRequest,Integer> {

}
