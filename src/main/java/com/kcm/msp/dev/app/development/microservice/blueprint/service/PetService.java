package com.kcm.msp.dev.app.development.microservice.blueprint.service;

import com.kcm.msp.dev.app.development.microservice.blueprint.models.Pet;
import java.util.List;

public interface PetService {
  List<Pet> listPets(Integer limit);

  Pet showPetById(String id);
}
