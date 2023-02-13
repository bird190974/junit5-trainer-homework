package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import com.dmdev.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private CreateSubscriptionValidator createSubscriptionValidator;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private CreateSubscriptionMapper createSubscriptionMapper;
    @Mock
    private Clock clock;
    @InjectMocks
    private SubscriptionService subscriptionService;
    private final Instant expirationDate = Instant.now().plusSeconds(100500);

    @Test
    void upsert() {
        CreateSubscriptionDto createSubscriptionDto = getCreateSubscriptionDto();
        Subscription subscription = getSubscription();



        doReturn(new ValidationResult()).when(createSubscriptionValidator).validate(createSubscriptionDto);
        doReturn(subscription).when(createSubscriptionMapper).map(createSubscriptionDto);
//
//
//
        Subscription actualResult = subscriptionService.upsert(createSubscriptionDto);





//        assertThat(actualResult).is(subscription);
    }

    private CreateSubscriptionDto getCreateSubscriptionDto() {
        return CreateSubscriptionDto.builder()
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE.name())
                .expirationDate(expirationDate)
                .build();
    }

    private Subscription getSubscription() {
        return Subscription.builder()
                .id(1)
                .userId(1)
                .name("Siarhei")
                .provider(Provider.APPLE)
                .expirationDate(expirationDate)
                .status(Status.ACTIVE)
                .build();
    }


}