package com.jobradar.candidate.presentation;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.jobradar.candidate.application.CandidateProfileCommand;
import com.jobradar.candidate.application.CandidateProfileService;
import com.jobradar.candidate.domain.CandidateProfile;
import com.jobradar.candidate.domain.WorkMode;
import com.jobradar.shared.infrastructure.security.SecurityConfig;
import com.jobradar.shared.presentation.GlobalExceptionHandler;

@WebMvcTest(CandidateProfileController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class CandidateProfileControllerTest {

    private static final UUID PROFILE_ID = UUID.fromString("10000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateProfileService candidateProfileService;

    @Test
    void getCurrentUserProfileReturnsProfile() throws Exception {
        when(candidateProfileService.findCurrentUserProfile()).thenReturn(Optional.of(profile()));

        mockMvc.perform(get("/api/candidate-profile/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PROFILE_ID.toString()))
                .andExpect(jsonPath("$.userId").value(USER_ID.toString()))
                .andExpect(jsonPath("$.fullName").value("Safouane"))
                .andExpect(jsonPath("$.targetWorkModes[0]").value("REMOTE"))
                .andExpect(jsonPath("$.skills[1]").value("Spring Boot"));
    }

    @Test
    void getCurrentUserProfileReturnsNotFoundWhenMissing() throws Exception {
        when(candidateProfileService.findCurrentUserProfile()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/candidate-profile/me"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/api/candidate-profile/me"));
    }

    @Test
    void putCurrentUserProfileUpsertsProfile() throws Exception {
        when(candidateProfileService.upsertCurrentUserProfile(any(CandidateProfileCommand.class)))
                .thenReturn(profile());

        mockMvc.perform(put("/api/candidate-profile/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Safouane",
                                  "headline": "Building reliable job search tools",
                                  "currentRole": "Backend Engineer",
                                  "yearsOfExperience": 5,
                                  "currentLocation": "Casablanca",
                                  "targetLocations": ["Casablanca", "Remote"],
                                  "targetWorkModes": ["REMOTE", "HYBRID"],
                                  "targetRoles": ["Backend Engineer"],
                                  "skills": ["Java", "Spring Boot"],
                                  "languages": ["English", "French"],
                                  "minSalary": 25000,
                                  "currency": "MAD"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Safouane"))
                .andExpect(jsonPath("$.currentRole").value("Backend Engineer"));

        verify(candidateProfileService).upsertCurrentUserProfile(any(CandidateProfileCommand.class));
    }

    @Test
    void putCurrentUserProfileValidatesRequiredFields() throws Exception {
        mockMvc.perform(put("/api/candidate-profile/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "",
                                  "yearsOfExperience": -1,
                                  "targetRoles": [],
                                  "skills": []
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors[*].field").value(hasItem("fullName")));
    }

    private static CandidateProfile profile() {
        CandidateProfile profile = new CandidateProfile(USER_ID);
        profile.update(
                "Safouane",
                "Building reliable job search tools",
                "Backend Engineer",
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
        ReflectionTestUtils.setField(profile, "id", PROFILE_ID);
        ReflectionTestUtils.setField(profile, "createdAt", Instant.parse("2026-06-08T20:00:00Z"));
        ReflectionTestUtils.setField(profile, "updatedAt", Instant.parse("2026-06-08T20:30:00Z"));
        return profile;
    }
}
