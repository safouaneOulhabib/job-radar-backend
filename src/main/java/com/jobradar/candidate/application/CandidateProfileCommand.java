package com.jobradar.candidate.application;

import java.util.List;

import com.jobradar.candidate.domain.WorkMode;

public record CandidateProfileCommand(
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
        String currency
) {
}
