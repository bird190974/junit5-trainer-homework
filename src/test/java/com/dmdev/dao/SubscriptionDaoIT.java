package com.dmdev.dao;

import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.integration.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDaoIT extends IntegrationTestBase {
    private final Instant expirationDate =  Instant.parse("2023-12-31T23:59:59.999Z");
    private final SubscriptionDao subscriptionDao = SubscriptionDao.getInstance();

    @Test
    void findAll() {
        Subscription subscription1 = subscriptionDao.insert(getSubscription(1));
        Subscription subscription2 = subscriptionDao.insert(getSubscription(2));
        Subscription subscription3 = subscriptionDao.insert(getSubscription(3));

        List<Subscription> actualResult = subscriptionDao.findAll();

        List<Integer> userIds = actualResult.stream()
                .map(Subscription::getId)
                .toList();

        assertThat(userIds).contains(subscription1.getUserId(), subscription2.getUserId(), subscription3.getUserId());
        assertThat(actualResult).hasSize(3);
    }

    @Test
    void findById() {
        Subscription expectedValue = subscriptionDao.insert(getSubscription(1));

        Optional<Subscription> actualResult = subscriptionDao.findById(expectedValue.getUserId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(expectedValue);
    }

    @Test
    void shouldNotFindByIdIfUserDoesNotExist() {
        subscriptionDao.insert(getSubscription(1));

        Optional<Subscription> actualResult = subscriptionDao.findById(2);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void deleteExistingEntity() {
        Subscription expectedValue = subscriptionDao.insert(getSubscription(1));

        boolean actualResult = subscriptionDao.delete(1);

        assertThat(actualResult).isTrue();
    }

    @Test
    void deleteNotExistingEntity() {
        subscriptionDao.insert(getSubscription(1));

        boolean actualResult = subscriptionDao.delete(2);

        assertThat(actualResult).isFalse();
    }

    @Test
    void update() {
        Subscription subscription = getSubscription(1);

        Subscription actualResult = subscriptionDao.update(subscription);

        assertThat(actualResult).isEqualTo(subscription);
    }


    @Test
    void insert() {
        Subscription subscription = getSubscription(1);

        Subscription actualResult = subscriptionDao.insert(subscription);

        assertThat(actualResult).isEqualTo(subscription);
    }

    @Test
    void findByUserId() {



    }

    private Subscription getSubscription(Integer userId) {
        return Subscription.builder()
                .userId(userId)
                .name("Siarhei")
                .provider(Provider.APPLE)
                .expirationDate(expirationDate)
                .status(Status.ACTIVE)
                .build();
    }

}