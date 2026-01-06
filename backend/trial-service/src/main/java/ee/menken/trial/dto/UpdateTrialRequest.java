package ee.menken.trial.dto;

import ee.menken.trial.entity.TrialStatus;
import jakarta.validation.constraints.*;

public record UpdateTrialRequest (
    @NotBlank @Size(min = 10, max = 100) String name,
    @Size(max = 200) String location,
    @NotNull TrialStatus status
) {}