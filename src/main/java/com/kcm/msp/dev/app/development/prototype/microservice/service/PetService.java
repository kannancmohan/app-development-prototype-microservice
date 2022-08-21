package com.kcm.msp.dev.app.development.prototype.microservice.service;

import com.kcm.msp.dev.app.development.prototype.microservice.models.CreatePetRequest;
import com.kcm.msp.dev.app.development.prototype.microservice.models.Pet;
import java.util.List;

public interface PetService {
  List<Pet> listPets(Integer limit);

  Pet showPetById(String id);

  Pet createPet(CreatePetRequest request);
}
