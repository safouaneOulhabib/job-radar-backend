package com.jobradar.candidate.application;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobradar.candidate.domain.CandidateProfile;
import com.jobradar.candidate.infrastructure.CandidateProfileRepository;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final CurrentUserProvider currentUserProvider;

    public CandidateProfileService(
            CandidateProfileRepository candidateProfileRepository,
            CurrentUserProvider currentUserProvider
    ) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Transactional(readOnly = true)
    public Optional<CandidateProfile> findCurrentUserProfile() {
        return candidateProfileRepository.findByUserId(currentUserProvider.currentUserId());
    }

    @Transactional
    public CandidateProfile upsertCurrentUserProfile(CandidateProfileCommand command) {
        UUID userId = currentUserProvider.currentUserId();
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseGet(() -> new CandidateProfile(userId));

        profile.update(
                command.fullName(),
                command.headline(),
                command.currentRole(),
                command.yearsOfExperience(),
                command.currentLocation(),
                command.targetLocations(),
                command.targetWorkModes(),
                command.targetRoles(),
                command.skills(),
                command.languages(),
                command.minSalary(),
                command.currency()
        );

        return candidateProfileRepository.save(profile);
    }
}
