package com.dmdev.validator;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionValidatorTest {

    private final CreateSubscriptionValidator validator = CreateSubscriptionValidator.getInstance();

    @Test
    void shouldPassValidation() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().plusSeconds(100500))
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertFalse(actualResult.hasErrors());
    }

    @Test
    void invalidUserId() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(null)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().plusSeconds(100500))
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(100);
    }

    @Test
    void invalidName() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().plusSeconds(100500))
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(101);
    }

    @Test
    void invalidProvider() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider("GAPPLE")
                .expirationDate(Instant.now().plusSeconds(100500))
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(102);
    }

    @Test
    void invalidExpirationDate() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(null)
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(103);
    }

    @Test
    void invalidExpirationDateIsBeforeNow() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().minusSeconds(100500))
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo(103);
    }

    @Test
    void invalidUserIdNameProviderExpirationDate() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(null)
                .name("")
                .provider("GAPPLE")
                .expirationDate(null)
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors().size()).isEqualTo(4);
        List<Integer> errorsCodeList = actualResult.getErrors().stream()
                .map(Error::getCode)
                .toList();
        assertThat(errorsCodeList).contains(100, 101, 102, 103);
    }
}