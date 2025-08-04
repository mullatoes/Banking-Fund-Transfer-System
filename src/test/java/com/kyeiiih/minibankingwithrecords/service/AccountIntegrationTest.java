package com.kyeiiih.minibankingwithrecords.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyeiiih.minibankingwithrecords.dto.AccountResponse;
import com.kyeiiih.minibankingwithrecords.dto.CreateAccountRequest;
import com.kyeiiih.minibankingwithrecords.dto.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAccount_shouldReturn200AndAccountResponse() {
        // Arrange
        CreateAccountRequest request = new CreateAccountRequest("Alice", new BigDecimal("500"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateAccountRequest> entity = new HttpEntity<>(request, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity("/api/accounts", entity, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Alice");
        assertThat(response.getBody()).contains("500");
    }
}
