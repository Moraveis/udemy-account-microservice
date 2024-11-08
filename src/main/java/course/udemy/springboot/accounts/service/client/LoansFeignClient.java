package course.udemy.springboot.accounts.service.client;

import course.udemy.springboot.accounts.dto.LoanDto;
import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/loans/fetch", consumes = MediaType.APPLICATION_JSON)
    ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam String mobileNumber);
}