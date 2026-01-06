package ee.menken.trial.controller;

import ee.menken.trial.entity.Trial;
import ee.menken.trial.dto.CreateTrialRequest;
import ee.menken.trial.dto.UpdateTrialRequest;
import ee.menken.trial.exception.TrialNotFoundException;
import ee.menken.trial.repository.TrialRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trials")
public class TrialController {
    private final TrialRepository trialRepository;

    public TrialController(TrialRepository trialRepository) {
        this.trialRepository = trialRepository;
    }

    @GetMapping
    public List<Trial> getTrials() {
        return trialRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Trial> createTrial(@Valid @RequestBody CreateTrialRequest request) {
        Trial trial = new Trial(
                request.name(),
                request.location(),
                request.status()
        );


        Trial saved = trialRepository.save(trial);

        return ResponseEntity
                .created(URI.create("/api/trials/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public Trial updateTrial(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrialRequest request
    ) {
        Trial existing = trialRepository.findById(id)
                .orElseThrow(() -> new TrialNotFoundException(id));

        existing.setName(request.name());
        existing.setLocation(request.location());
        existing.setStatus(request.status());

        return trialRepository.save(existing);
    }
}
