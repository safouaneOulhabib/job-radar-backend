package com.jobradar.candidate.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobradar.candidate.application.CandidateProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidate-profile")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(CandidateProfileService candidateProfileService) {
        this.candidateProfileService = candidateProfileService;
    }

    @GetMapping("/me")
    public CandidateProfileResponse getCurrentUserProfile() {
        return candidateProfileService.findCurrentUserProfile()
                .map(CandidateProfileResponse::from)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/me")
    public CandidateProfileResponse upsertCurrentUserProfile(@Valid @RequestBody CandidateProfileRequest request) {
        return CandidateProfileResponse.from(candidateProfileService.upsertCurrentUserProfile(request.toCommand()));
    }
}
