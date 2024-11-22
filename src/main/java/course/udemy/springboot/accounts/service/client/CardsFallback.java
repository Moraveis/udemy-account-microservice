package course.udemy.springboot.accounts.service.client;

import course.udemy.springboot.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
