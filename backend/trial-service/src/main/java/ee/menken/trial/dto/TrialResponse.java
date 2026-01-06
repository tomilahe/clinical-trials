package ee.menken.trial.dto;

import ee.menken.trial.entity.TrialStatus;

public record TrialResponse(
        Long id,
        String name,
        String location,
        TrialStatus status
) {
}
