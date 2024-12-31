package course.udemy.springboot.accounts.functions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountsFunctions {

    public Consumer<Long> updateCommunication() {
        return accountNumber -> {
            log.info("Updating account number: {}", accountNumber);
        };
    }
}
