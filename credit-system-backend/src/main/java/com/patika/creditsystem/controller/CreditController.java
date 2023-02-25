package com.patika.creditsystem.controller;

import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.request.CreateCreditDto;
import com.patika.creditsystem.service.CreditService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credits")
public class CreditController {
    private final CreditService creditService;

    private final Logger LOGGER = LoggerFactory.getLogger(CreditController.class);

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    public ResponseEntity<List<CreditDto>> getAllCredit(){
        LOGGER.info("Controller: request to list  all credit data");
        return ResponseEntity.ok(creditService.getAllCredit());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDto> getOneCreditById(@PathVariable("id") String id){
        LOGGER.info("Controller: request to fetch a credit by id : ");
        return ResponseEntity.ok(creditService.getOneCreditById(id));
    }

    @PostMapping
    public ResponseEntity<CreditDto> createCredit(@RequestBody @Valid CreateCreditDto request){
        LOGGER.info("Controller: request create credit");
        return new ResponseEntity<>(creditService.createCredit(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditDto> updateCreditById(@PathVariable("id") String id){
        LOGGER.info("Controller: request to update credit data {}",id);
        return ResponseEntity.ok(creditService.updateCredit(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CreditDto> deleteCreditById(@PathVariable("id") String id){
        LOGGER.info("Controller: request to delete credit id: {}", id);
        return ResponseEntity.ok(creditService.deleteCreditById(id));
    }

}
