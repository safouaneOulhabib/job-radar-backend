package com.jobradar.candidate.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobradar.candidate.domain.CandidateProfile;
import com.jobradar.candidate.domain.WorkMode;
import com.jobradar.candidate.infrastructure.CandidateProfileRepository;

@ExtendWith(MockitoExtension.class)
class CandidateProfileServiceTest {

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private CandidateProfileService candidateProfileService;

    @Test
    void findsCurrentUserProfile() {
        CandidateProfile profile = new CandidateProfile(USER_ID);
        when(currentUserProvider.currentUserId()).thenReturn(USER_ID);
        when(candidateProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(profile));

        Optional<CandidateProfile> result = candidateProfileService.findCurrentUserProfile();

        assertThat(result).contains(profile);
    }

    @Test
    void createsProfileWhenMissing() {
        CandidateProfileCommand command = command("Safouane", "Backend Engineer");
        when(currentUserProvider.currentUserId()).thenReturn(USER_ID);
        when(candidateProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(candidateProfileRepository.save(any(CandidateProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CandidateProfile result = candidateProfileService.upsertCurrentUserProfile(command);

        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getFullName()).isEqualTo("Safouane");
        assertThat(result.getTargetWorkModes()).containsExactly(WorkMode.REMOTE, WorkMode.HYBRID);
        assertThat(result.getTargetRoles()).containsExactly("Backend Engineer");
        assertThat(result.getSkills()).containsExactly("Java", "Spring Boot");
        verify(candidateProfileRepository).save(result);
    }

    @Test
    void updatesExistingProfile() {
        CandidateProfile profile = new CandidateProfile(USER_ID);
        profile.update(
                "Old Name",
                null,
                null,
                1,
                null,
                List.of(),
                List.of(),
                List.of("Developer"),
                List.of("Java"),
                List.of(),
                null,
                null
        );
        when(currentUserProvider.currentUserId()).thenReturn(USER_ID);
        when(candidateProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(profile));
        when(candidateProfileRepository.save(any(CandidateProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CandidateProfile result = candidateProfileService.upsertCurrentUserProfile(command("New Name", "Platform Engineer"));

        assertThat(result).isSameAs(profile);
        assertThat(result.getFullName()).isEqualTo("New Name");
        assertThat(result.getCurrentRole()).isEqualTo("Platform Engineer");
        assertThat(result.getYearsOfExperience()).isEqualTo(5);

        ArgumentCaptor<CandidateProfile> captor = ArgumentCaptor.forClass(CandidateProfile.class);
        verify(candidateProfileRepository).save(captor.capture());
        assertThat(captor.getValue()).isSameAs(profile);
    }

    private static CandidateProfileCommand command(String fullName, String currentRole) {
        return new CandidateProfileCommand(
                fullName,
                "Building reliable job search tools",
                currentRole,
                5,
                "Casablanca",
                List.of("Casablanca", "Remote"),
                List.of(WorkMode.REMOTE, WorkMode.HYBRID),
                List.of("Backend Engineer"),
                List.of("Java", "Spring Boot"),
                List.of("English", "French"),
                25000,
                "MAD"
        );
    }
}
