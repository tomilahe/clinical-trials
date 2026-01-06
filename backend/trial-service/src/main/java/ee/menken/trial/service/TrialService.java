package ee.menken.trial.service;

import ee.menken.trial.entity.Trial;
import ee.menken.trial.dto.CreateTrialRequest;
import ee.menken.trial.dto.UpdateTrialRequest;
import ee.menken.trial.exception.TrialNotFoundException;
import ee.menken.trial.repository.TrialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrialService {

    private final TrialRepository trialRepository;

    public TrialService(TrialRepository trialRepository) {
        this.trialRepository = trialRepository;
    }

    @Transactional(readOnly = true)
    public List<Trial> list() {
        return trialRepository.findAll();
    }

    public Trial create(CreateTrialRequest request) {
        Trial trial = new Trial(request.name(), request.location(), request.status());
        return trialRepository.save(trial);
    }

    public Trial update(Long id, UpdateTrialRequest request) {
        Trial existing = trialRepository.findById(id)
                .orElseThrow(() -> new TrialNotFoundException(id));

        existing.setName(request.name());
        existing.setLocation(request.location());
        existing.setStatus(request.status());

        return trialRepository.save(existing);
    }
}
