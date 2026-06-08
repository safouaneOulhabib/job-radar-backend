package com.jobradar.candidate.presentation;

import java.util.List;

import com.jobradar.candidate.application.CandidateProfileCommand;
import com.jobradar.candidate.domain.WorkMode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CandidateProfileRequest(
        @NotBlank String fullName,
        String headline,
        String currentRole,
        @Min(0) Integer yearsOfExperience,
        String currentLocation,
        List<String> targetLocations,
        List<WorkMode> targetWorkModes,
        @NotEmpty List<String> targetRoles,
        @NotEmpty List<String> skills,
        List<String> languages,
        Integer minSalary,
        String currency
) {

    CandidateProfileCommand toCommand() {
        return new CandidateProfileCommand(
                fullName,
                headline,
                currentRole,
                yearsOfExperience == null ? 0 : yearsOfExperience,
                currentLocation,
                targetLocations,
                targetWorkModes,
                targetRoles,
                skills,
                languages,
                minSalary,
                currency
        );
    }
}
