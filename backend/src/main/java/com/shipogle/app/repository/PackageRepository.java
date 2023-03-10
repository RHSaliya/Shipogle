package com.shipogle.app.repository;

import com.shipogle.app.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,Integer> {
}
