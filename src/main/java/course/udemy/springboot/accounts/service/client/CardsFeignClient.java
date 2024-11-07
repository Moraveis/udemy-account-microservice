package course.udemy.springboot.accounts.service.client;

import course.udemy.springboot.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cards")
public interface CardsFeignClient {

    @GetMapping("/api/cards/fetch")
    ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber);
}
