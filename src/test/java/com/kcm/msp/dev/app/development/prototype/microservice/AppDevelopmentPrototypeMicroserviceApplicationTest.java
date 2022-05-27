package com.kcm.msp.dev.app.development.prototype.microservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kcm.msp.dev.app.development.prototype.microservice.controller.PrototypeController;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.DisabledIf;

@Tag("IntegrationTest")
@DisabledIf(expression = "#{environment['skip.integration.test'] == 'true'}")
@SpringBootTest
class AppDevelopmentPrototypeMicroserviceApplicationTest {

  @Autowired PrototypeController prototypeController;

  @Test
  void contextLoads() {
    assertNotNull(prototypeController, () -> "The prototypeController should not be null");
  }
}
