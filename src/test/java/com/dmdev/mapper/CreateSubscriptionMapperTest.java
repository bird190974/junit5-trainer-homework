package com.dmdev.mapper;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionMapperTest {

    private final CreateSubscriptionMapper mapper = CreateSubscriptionMapper.getInstance();
    private final Instant expirationDate =  Instant.parse("2023-12-31T23:59:59.999Z");

    @Test
    void map(){


        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(expirationDate)
                .build();

        Subscription actualResult = mapper.map(dto);

        Subscription expectedResult = Subscription.builder()
                .id(null)
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE)
                .expirationDate(expirationDate)
                .status(Status.ACTIVE)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}