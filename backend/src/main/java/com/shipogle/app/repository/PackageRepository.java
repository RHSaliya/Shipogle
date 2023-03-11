package com.shipogle.app.repository;

import com.shipogle.app.model.Package;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package,Integer> {

    List<Package> getAllBySender(User sender);

//    Package getPackageByPackage_id(Integer id);
    Package getPackageById(Integer id);

}
