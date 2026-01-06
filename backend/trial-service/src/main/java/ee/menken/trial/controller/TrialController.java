package ee.menken.trial.controller;

import ee.menken.trial.dto.CreateTrialRequest;
import ee.menken.trial.dto.TrialResponse;
import ee.menken.trial.dto.UpdateTrialRequest;
import ee.menken.trial.mapper.TrialMapper;
import ee.menken.trial.service.TrialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trials")
public class TrialController {

    private final TrialService trialService;

    public TrialController(TrialService trialService) {
        this.trialService = trialService;
    }

    @GetMapping
    public List<TrialResponse> getTrials() {
        return trialService.list().stream()
                .map(TrialMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TrialResponse> createTrial(@Valid @RequestBody CreateTrialRequest request) {
        var saved = trialService.create(request);
        return ResponseEntity
                .created(URI.create("/api/trials/" + saved.getId()))
                .body(TrialMapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrialResponse> updateTrial(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrialRequest request
    ) {
        var updated = trialService.update(id, request);
        return ResponseEntity.ok(TrialMapper.toResponse(updated));
    }
}
