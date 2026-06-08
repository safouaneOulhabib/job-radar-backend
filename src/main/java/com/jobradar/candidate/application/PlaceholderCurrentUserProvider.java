package com.jobradar.candidate.application;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class PlaceholderCurrentUserProvider implements CurrentUserProvider {

    private static final UUID TEMPORARY_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public UUID currentUserId() {
        return TEMPORARY_USER_ID;
    }
}
