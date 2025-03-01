package com.harvest.harvestservice.Service;

import com.google.common.net.HttpHeaders;
import com.harvest.harvestservice.Dto.ApiResponseDto;
import com.harvest.harvestservice.Dto.MembersDto;
import com.harvest.harvestservice.Entity.Harvest;
import com.harvest.harvestservice.Entity.Section;
import com.harvest.harvestservice.Repository.HarvestRepository;
import com.harvest.harvestservice.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HarvestServiceImp implements HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private EmailService emailService;

    @Override
    public Harvest createHarvest(Harvest harvest, String token) {
        Harvest savedHarvest = harvestRepository.save(harvest);

        // Get all members using the provided token
        ApiResponseDto apiResponseDto = getAllMembers(token);
        List<MembersDto> membersDtoList = apiResponseDto.getMembersDtoList();

        // Extract email addresses
        List<String> emails = membersDtoList.stream()
                .map(MembersDto::getEmail)
                .collect(Collectors.toList());

        // Send notification
        emailService.sendHarvestNotification(emails);

        return savedHarvest;
    }



    @Override
    public Harvest updateHarvest(Long id, Harvest updatedHarvest) {
        Harvest existingHarvest = harvestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Harvest Not Found"));

        existingHarvest.setStartedDate(updatedHarvest.getStartedDate());
        existingHarvest.setEndDate(updatedHarvest.getEndDate());
        existingHarvest.setSupervise(updatedHarvest.getSupervise());
        existingHarvest.setSections(updatedHarvest.getSections());

        return harvestRepository.save(existingHarvest);
    }

    @Override
    public void deleteHarvestById(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Harvest Not Found"));
        harvestRepository.delete(harvest);
    }

    @Override
    public List<Harvest> getAllHarvest() {
        return harvestRepository.findAll();
    }

    @Override
    public Harvest getHarvestById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Harvest not found"));
    }

    @Override
    public List<Harvest> searchHarvest(String query) {
        return harvestRepository.searchHarvest(query);
    }

    @Override
    public void deleteAllHarvest() {
        harvestRepository.deleteAll();
    }

    @Override
    public ApiResponseDto getAllMembers(String token) {
        Mono<List<MembersDto>> listMono = webClient.get()
                .uri("https://distinguished-education-production.up.railway.app/api/v1/members")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(MembersDto.class)
                .collectList();

        List<MembersDto> membersDtoList = listMono.block();

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setMembersDtoList(membersDtoList);
        return apiResponseDto;
    }

    @Override
    public Harvest findByHarvestId(String harvestId) {
        // Assuming your repository method is defined as findFirstByHarvestId(...)
        return harvestRepository.findFirstByHarvestId(harvestId);
    }
}
