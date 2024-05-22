package com.core.linkup.office.repository;

import com.core.linkup.office.entity.OfficeBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeBuilding, Long> {

    OfficeBuilding findOfficeBuildingById(Long officeBuildingId);
}
