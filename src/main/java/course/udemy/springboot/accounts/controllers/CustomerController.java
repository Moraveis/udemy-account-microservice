package course.udemy.springboot.accounts.controllers;

import course.udemy.springboot.accounts.dto.CustomerDetailsDto;
import course.udemy.springboot.accounts.dto.ErrorResponseDto;
import course.udemy.springboot.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Custumers",
        description = "CRUD REST APIs to FETCH customer details"
)
@RestController
@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/fetch-customer-details")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("course-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        log.debug("course-correlation-id found: {}", correlationId);
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.ok(customerDetailsDto);
    }
}
