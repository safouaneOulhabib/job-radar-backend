CREATE TABLE candidate_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    headline VARCHAR(255),
    current_role VARCHAR(255),
    years_of_experience INTEGER NOT NULL CHECK (years_of_experience >= 0),
    current_location VARCHAR(255),
    target_locations JSONB NOT NULL DEFAULT '[]'::jsonb,
    target_work_modes JSONB NOT NULL DEFAULT '[]'::jsonb,
    target_roles JSONB NOT NULL DEFAULT '[]'::jsonb,
    skills JSONB NOT NULL DEFAULT '[]'::jsonb,
    languages JSONB NOT NULL DEFAULT '[]'::jsonb,
    min_salary INTEGER,
    currency VARCHAR(3),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_candidate_profiles_user_id ON candidate_profiles (user_id);
