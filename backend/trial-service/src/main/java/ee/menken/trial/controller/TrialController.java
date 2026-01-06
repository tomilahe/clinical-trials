package ee.menken.trial.controller;

import ee.menken.trial.dto.TrialResponse;
import ee.menken.trial.dto.CreateTrialRequest;
import ee.menken.trial.dto.UpdateTrialRequest;
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
        return trialService.list();
    }

    @PostMapping
    public ResponseEntity<TrialResponse> create(@Valid @RequestBody CreateTrialRequest req) {
        TrialResponse created = trialService.create(req);
        return ResponseEntity
                .created(URI.create("/api/trials/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrialResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrialRequest req
    ) {
        return ResponseEntity.ok(trialService.update(id, req));
    }
}
