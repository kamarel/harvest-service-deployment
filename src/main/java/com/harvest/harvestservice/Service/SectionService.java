package com.harvest.harvestservice.Service;

import com.harvest.harvestservice.Entity.Section;

public interface SectionService {

    Section addSectionInHarvest(String harvestId, Section section);

    Section updatedSection (Long id, Section sectionToUpdate);

    void deleteSectionById(Long id);
}
