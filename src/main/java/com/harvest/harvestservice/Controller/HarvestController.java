package com.harvest.harvestservice.Controller;

import com.harvest.harvestservice.Entity.Harvest;
import com.harvest.harvestservice.Entity.Section;
import com.harvest.harvestservice.Service.HarvestServiceImp;
import com.harvest.harvestservice.Service.SectionServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Harvest Controller",
        description = "Exposes REST API endpoints for managing harvests"
)
@RestController
@RequestMapping("/api/harvest")  // Updated path to include "/api" for clarity
@AllArgsConstructor
public class HarvestController {

    private final HarvestServiceImp harvestServiceImp;
    private final SectionServiceImp sectionServiceImp;



    @Operation(
            summary = "Update an existing Harvest",
            description = "Allows the user to update an existing Harvest by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Harvest updated successfully"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Harvest> updateHarvest(@PathVariable Long id, @RequestBody Harvest harvest) {
        Harvest updatedHarvest = harvestServiceImp.updateHarvest(id, harvest);
        return new ResponseEntity<>(updatedHarvest, HttpStatus.OK);  // Return 200 OK
    }


    @Operation(
            summary = "Create a new Harvest entry",
            description = "Allows the user to add a new Harvest"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Harvest created successfully"
    )
    @PostMapping
    public ResponseEntity<Harvest> createHarvest(@RequestBody Harvest harvest) {
        Harvest createdHarvest = harvestServiceImp.createharvest(harvest);
        return new ResponseEntity<>(createdHarvest, HttpStatus.CREATED);  // Return 201 CREATED
    }



    @Operation(
            summary = "Get a Harvest by ID",
            description = "Retrieve a specific Harvest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Harvest found"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<Harvest> getHarvestById(@PathVariable Long id) {
        Harvest harvest = harvestServiceImp.getHarvestById(id);
        return new ResponseEntity<>(harvest, HttpStatus.OK);  // Use 200 OK instead of 302 FOUND
    }

    @Operation(
            summary = "Delete a Harvest by ID",
            description = "Delete a specific Harvest by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Harvest deleted successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHarvestById(@PathVariable Long id) {
        harvestServiceImp.deleteHarvestById(id);
        return new ResponseEntity<>("Harvest Deleted", HttpStatus.OK);  // Return success message
    }

    @Operation(
            summary = "Delete all Harvest records",
            description = "Delete all Harvest records from the system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "All Harvest records deleted"
    )
    @DeleteMapping
    public ResponseEntity<String> deleteAllHarvest() {
        harvestServiceImp.deleteAllHarvest();
        return new ResponseEntity<>("All Harvest records deleted", HttpStatus.OK);  // Return success message
    }

    @Operation(
            summary = "Add a Section to a Harvest",
            description = "Add a new Section to an existing Harvest"
    )
    @PostMapping("/{harvestId}/add")
    public ResponseEntity<Section> addSectionToHarvest(@PathVariable String harvestId, @RequestBody Section section) {
        Section savedSection = sectionServiceImp.addSectionInHarvest(harvestId, section);
        return new ResponseEntity<>(savedSection, HttpStatus.OK);  // Return 201 CREATED for new section
    }

    @Operation(
            summary = "Update Section information within a Harvest",
            description = "Update the details of a Section for a specific Harvest"
    )
    @PutMapping("/{id}/section")
    public ResponseEntity<Section> updateSectionInfo(@PathVariable Long id, @RequestBody Section sectionToUpdate) {
        Section updatedSection = sectionServiceImp.updatedSection(id, sectionToUpdate);
        return new ResponseEntity<>(updatedSection, HttpStatus.OK);  // Return updated section
    }

    @Operation(
            summary = "Delete a Section from a Harvest",
            description = "Delete a Section by its ID from a Harvest"
    )
    @DeleteMapping("/{id}/delete/section")
    public ResponseEntity<String> deleteSectionById(@PathVariable Long id) {
        sectionServiceImp.deleteSectionById(id);
        return new ResponseEntity<>("Section deleted successfully", HttpStatus.OK);  // Return success message
    }



    @Operation(
            summary = "Search Harvest records by query",
            description = "Search Harvest records using a query parameter"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search results returned"
    )
    @GetMapping("/search")  // Changed the path to make it more REST-friendly
    public ResponseEntity<List<Harvest>> searchHarvest(@RequestParam String query) {
        List<Harvest> harvestList = harvestServiceImp.searchHarvest(query);
        return new ResponseEntity<>(harvestList, HttpStatus.OK);  // Return search results with 200 OK
    }


    @Operation(
            summary = "Get all Harvest records",
            description = "Retrieve all Harvest records"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of Harvest records retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<Harvest>> getAllHarvest() {
        List<Harvest> allHarvests = harvestServiceImp.getAllHarvest();
        return new ResponseEntity<>(allHarvests, HttpStatus.OK);  // Use 200 OK instead of 302 FOUND
    }
}
