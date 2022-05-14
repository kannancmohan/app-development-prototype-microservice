package com.kcm.msp.dev.app.development.microservice.blueprint.controller;

import static org.springframework.http.HttpStatus.OK;

import com.kcm.msp.dev.app.development.microservice.blueprint.api.BlueprintApi;
import com.kcm.msp.dev.app.development.microservice.blueprint.models.Pet;
import com.kcm.msp.dev.app.development.microservice.blueprint.service.PetService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class BlueprintController implements BlueprintApi {

  private final PetService petService;

  @Override
  public ResponseEntity<List<Pet>> listPets(final Integer limit) {
    final List<Pet> pets = petService.listPets(limit);
    return ResponseEntity.status(OK).body(pets);
  }

  @Override
  public ResponseEntity<Pet> showPetById(final String id) {
    final Pet pet = petService.showPetById(id);
    return ResponseEntity.status(OK).body(pet);
  }
}
