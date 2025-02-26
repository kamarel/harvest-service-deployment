package com.harvest.harvestservice.Repository;

import com.harvest.harvestservice.Entity.Harvest;
import com.harvest.harvestservice.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {



}
