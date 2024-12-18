package course.udemy.springboot.accounts.service.client;

import course.udemy.springboot.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CardDto> fetchCardDetails(@RequestHeader("course-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
