package course.udemy.springboot.accounts.service.client;

import course.udemy.springboot.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoanDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
