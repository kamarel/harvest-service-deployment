package com.harvest.harvestservice.Service;


import com.harvest.harvestservice.Entity.Harvest;
import com.harvest.harvestservice.Entity.Section;
import com.harvest.harvestservice.Repository.HarvestRepository;
import com.harvest.harvestservice.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SectionServiceImp implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private HarvestRepository harvestRepository;

    @Override
    public Section addSectionInHarvest(String harvestId, Section section) {
        Harvest harvest = harvestRepository.findByHarvestId(harvestId);

        section.setHarvest(harvest);

        return sectionRepository.save(section);
    }

    @Override
    public Section updatedSection(Long id, Section sectionToUpdate) {

        Optional<Section> optionalSection = sectionRepository.findById(id);

        if(!optionalSection.isPresent()){
            throw new RuntimeException("The section does not exist");
        }

        Section existSection = optionalSection.get();

        existSection.setSectionAmount(sectionToUpdate.getSectionAmount());
        existSection.setSectionLeader(sectionToUpdate.getSectionLeader());
        existSection.setSectionName(sectionToUpdate.getSectionName());

        return sectionRepository.save(existSection);
    }

    @Override
    public void deleteSectionById(Long id) {
        sectionRepository.deleteById(id);
    }
}
