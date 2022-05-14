package com.kcm.msp.dev.app.development.microservice.blueprint.service.impl;

import com.kcm.msp.dev.app.development.microservice.blueprint.exception.ItemNotFoundException;
import com.kcm.msp.dev.app.development.microservice.blueprint.models.Pet;
import com.kcm.msp.dev.app.development.microservice.blueprint.service.PetService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

  @Override
  public List<Pet> listPets(final Integer limit) {
    final Pet pet = new Pet().id(123L).name("petName").tag("petTag");
    return List.of(pet);
  }

  @Override
  public Pet showPetById(String id) {
    if (StringUtils.isBlank(id)) {
      throw new ItemNotFoundException("Item not found");
    }
    return new Pet().id(123L).name("petName").tag("petTag");
  }
}
