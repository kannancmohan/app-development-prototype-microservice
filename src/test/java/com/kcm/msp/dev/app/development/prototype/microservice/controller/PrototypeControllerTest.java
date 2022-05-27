package com.kcm.msp.dev.app.development.prototype.microservice.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.kcm.msp.dev.app.development.prototype.microservice.models.Pet;
import com.kcm.msp.dev.app.development.prototype.microservice.service.PetService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
final class PrototypeControllerTest {
  @Mock private PetService petService;
  private PrototypeController classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new PrototypeController(petService);
  }

  @Nested
  class TestGetPets {

    @BeforeEach
    void beforeEach() {
      when(petService.listPets(any())).thenReturn(List.of(getPetInstance()));
    }

    @Test
    void petsShouldReturnListOfPets() {
      final ResponseEntity<List<Pet>> responseEntity = classUnderTest.listPets(null);
      assertNotNull(responseEntity);
      assertNotNull(responseEntity.getBody());
      assertAll(
          () -> assertEquals(OK, responseEntity.getStatusCode()),
          () -> assertTrue(responseEntity.hasBody()),
          () -> assertFalse(responseEntity.getBody().isEmpty()));
    }
  }

  @Nested
  class TestShowPetById {

    @BeforeEach
    void beforeEach() {
      when(petService.showPetById(any())).thenReturn(getPetInstance());
    }

    @Test
    void showPetByIdShouldReturnPet() {
      final ResponseEntity<Pet> responseEntity = classUnderTest.showPetById(null);
      assertNotNull(responseEntity);
      assertAll(
          () -> assertEquals(OK, responseEntity.getStatusCode()),
          () -> assertTrue(responseEntity.hasBody()),
          () -> assertNotNull(responseEntity.getBody()));
    }
  }

  private Pet getPetInstance() {
    return new Pet().id(123L).name("petName").tag("petTag");
  }
}
