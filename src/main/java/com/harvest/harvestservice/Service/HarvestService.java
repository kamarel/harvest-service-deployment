package com.harvest.harvestservice.Service;

import com.harvest.harvestservice.Dto.ApiResponseDto;
import com.harvest.harvestservice.Entity.Harvest;

import java.util.List;

public interface HarvestService {

    Harvest createharvest(Harvest harvest);

    Harvest updateHarvest(Long id, Harvest updatedHarvest);

    void deleteHarvestById(Long id);

    List<Harvest>getAllHarvest();

    Harvest getHarvestById(Long id);

    List<Harvest> searchHarvest(String query);
    void deleteAllHarvest();

    ApiResponseDto getAllMembers();

    Harvest findByHarvestId(String harvestId);
}
