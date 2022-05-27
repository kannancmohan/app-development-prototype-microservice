package com.kcm.msp.dev.app.development.prototype.microservice.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.kcm.msp.dev.app.development.prototype.microservice.exception.ItemNotFoundException;
import com.kcm.msp.dev.app.development.prototype.microservice.models.Error;
import com.kcm.msp.dev.app.development.prototype.microservice.models.Pet;
import com.kcm.msp.dev.app.development.prototype.microservice.service.PetService;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.DisabledIf;

@Tag("IntegrationTest")
@DisabledIf(expression = "#{environment['skip.integration.test'] == 'true'}")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
final class PrototypeControllerIntegrationTest {
  private static final String TEST_URL = "http://localhost:";

  @LocalServerPort int port;

  @Autowired TestRestTemplate restTemplate;

  // comment this and its references to do a real integration test
  @MockBean private PetService petService;

  @Nested
  class TestGetPets {

    @Test
    @DisplayName("GET /pets should return list of pets")
    void petsShouldReturnListOfPets() throws Exception {
      when(petService.listPets(any())).thenReturn(List.of(getPetInstance()));
      final String url = new URL(TEST_URL + port + "/pets").toString();
      final ResponseEntity<List<Pet>> responseEntity =
          restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      assertNotNull(responseEntity);
      assertNotNull(responseEntity.getBody());
      assertAll(
          () -> assertEquals(OK, responseEntity.getStatusCode()),
          () -> assertTrue(responseEntity.hasBody()),
          () -> assertFalse(responseEntity.getBody().isEmpty()));
    }

    @Test
    @DisplayName("GET /pets should return INTERNAL_SERVER_ERROR")
    void petsShouldReturnError() throws Exception {
      when(petService.listPets(any())).thenThrow(new RuntimeException("some error occurred"));
      final String url = new URL(TEST_URL + port + "/pets").toString();
      final ResponseEntity<Error> responseEntity =
          restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      assertNotNull(responseEntity);
      assertEquals(INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
  }

  @Nested
  class TestGetShowPetById {

    @Test
    @DisplayName("GET /pets/{petId} should return a pet")
    void showPetByIdShouldReturnPet() throws Exception {
      when(petService.showPetById(any())).thenReturn(getPetInstance());
      final String url = new URL(TEST_URL + port + "/pets/123").toString();
      final ResponseEntity<Pet> responseEntity =
          restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      assertNotNull(responseEntity);
      assertAll(
          () -> assertEquals(OK, responseEntity.getStatusCode()),
          () -> assertNotNull(responseEntity.getBody()));
    }

    @Test
    @DisplayName("GET /pets/{petId} should return NOT_FOUND")
    void showPetByIdShouldReturnException() throws Exception {
      when(petService.showPetById(any())).thenThrow(new ItemNotFoundException("item not found"));
      final String url = new URL(TEST_URL + port + "/pets/123").toString();
      final ResponseEntity<Error> responseEntity =
          restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      assertNotNull(responseEntity);
      assertAll(
          () -> assertEquals(NOT_FOUND, responseEntity.getStatusCode()),
          () -> assertNotNull(responseEntity.getBody()));
    }
  }

  private Pet getPetInstance() {
    return new Pet().id(123L).name("petName").tag("petTag");
  }
}
