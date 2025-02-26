package com.harvest.harvestservice.Repository;

import com.harvest.harvestservice.Entity.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    @Query("SELECT p FROM Harvest p WHERE " +
            "CAST(p.id AS string) LIKE CONCAT('%', :query, '%') " +
            "OR p.supervise LIKE CONCAT('%', :query, '%')")
    List<Harvest> searchHarvest(@Param("query") String query);

    Harvest findByHarvestId(String harvestId);
}
