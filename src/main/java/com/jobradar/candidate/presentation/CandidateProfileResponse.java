package com.jobradar.candidate.presentation;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.jobradar.candidate.domain.CandidateProfile;
import com.jobradar.candidate.domain.WorkMode;

public record CandidateProfileResponse(
        UUID id,
        UUID userId,
        String fullName,
        String headline,
        String currentRole,
        Integer yearsOfExperience,
        String currentLocation,
        List<String> targetLocations,
        List<WorkMode> targetWorkModes,
        List<String> targetRoles,
        List<String> skills,
        List<String> languages,
        Integer minSalary,
        String currency,
        Instant createdAt,
        Instant updatedAt
) {

    static CandidateProfileResponse from(CandidateProfile profile) {
        return new CandidateProfileResponse(
                profile.getId(),
                profile.getUserId(),
                profile.getFullName(),
                profile.getHeadline(),
                profile.getCurrentRole(),
                profile.getYearsOfExperience(),
                profile.getCurrentLocation(),
                profile.getTargetLocations(),
                profile.getTargetWorkModes(),
                profile.getTargetRoles(),
                profile.getSkills(),
                profile.getLanguages(),
                profile.getMinSalary(),
                profile.getCurrency(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
