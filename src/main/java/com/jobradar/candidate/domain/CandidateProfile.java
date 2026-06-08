package com.jobradar.candidate.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private String fullName;

    private String headline;

    private String currentRole;

    @Column(nullable = false)
    private Integer yearsOfExperience;

    private String currentLocation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> targetLocations = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<WorkMode> targetWorkModes = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> targetRoles = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> skills = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> languages = new ArrayList<>();

    private Integer minSalary;

    private String currency;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public CandidateProfile() {
    }

    public CandidateProfile(UUID userId) {
        this.userId = userId;
    }

    public void update(
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
        this.fullName = fullName;
        this.headline = headline;
        this.currentRole = currentRole;
        this.yearsOfExperience = yearsOfExperience;
        this.currentLocation = currentLocation;
        this.targetLocations = copyOf(targetLocations);
        this.targetWorkModes = copyOf(targetWorkModes);
        this.targetRoles = copyOf(targetRoles);
        this.skills = copyOf(skills);
        this.languages = copyOf(languages);
        this.minSalary = minSalary;
        this.currency = currency;
    }

    private static <T> List<T> copyOf(List<T> values) {
        return values == null ? new ArrayList<>() : new ArrayList<>(values);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public List<String> getTargetLocations() {
        return List.copyOf(targetLocations);
    }

    public List<WorkMode> getTargetWorkModes() {
        return List.copyOf(targetWorkModes);
    }

    public List<String> getTargetRoles() {
        return List.copyOf(targetRoles);
    }

    public List<String> getSkills() {
        return List.copyOf(skills);
    }

    public List<String> getLanguages() {
        return List.copyOf(languages);
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public String getCurrency() {
        return currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
