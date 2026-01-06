package ee.menken.trial.mapper;

import ee.menken.trial.dto.TrialResponse;
import ee.menken.trial.entity.Trial;
import org.springframework.stereotype.Component;

@Component
public class TrialMapper {
    private TrialMapper() {
    }

    public static TrialResponse toResponse(Trial t) {
        return new TrialResponse(t.getId(), t.getName(), t.getLocation(), t.getStatus());
    }
}
