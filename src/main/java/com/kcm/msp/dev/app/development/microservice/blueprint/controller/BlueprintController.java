package com.kcm.msp.dev.app.development.microservice.blueprint.controller;

import static org.springframework.http.HttpStatus.OK;

import com.kcm.msp.dev.app.development.microservice.blueprint.api.BlueprintApi;
import com.kcm.msp.dev.app.development.microservice.blueprint.models.Pet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BlueprintController implements BlueprintApi {

  @Override
  public ResponseEntity<List<Pet>> listPets(Integer limit) {
    final Pet pet = new Pet();
    pet.setId(123L);
    pet.setName("petName");
    pet.setTag("petTag");
    return ResponseEntity.status(OK).body(List.of(pet));
  }
}
