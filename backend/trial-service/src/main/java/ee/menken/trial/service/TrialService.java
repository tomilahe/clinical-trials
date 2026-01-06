package ee.menken.trial.service;

import ee.menken.trial.dto.TrialResponse;
import ee.menken.trial.entity.Trial;
import ee.menken.trial.dto.CreateTrialRequest;
import ee.menken.trial.dto.UpdateTrialRequest;
import ee.menken.trial.exception.TrialNotFoundException;
import ee.menken.trial.mapper.TrialMapper;
import ee.menken.trial.repository.TrialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrialService {

    private final TrialRepository trialRepository;
    private final TrialMapper trialMapper;

    public TrialService(TrialRepository trialRepository, TrialMapper trialMapper) {
        this.trialRepository = trialRepository;
        this.trialMapper = trialMapper;
    }

    @Transactional(readOnly = true)
    public List<TrialResponse> list() {
        return trialRepository.findAll().stream()
                .map(trialMapper::toResponse)
                .toList();
    }

    public TrialResponse create(CreateTrialRequest req) {
        Trial t = new Trial(req.name(), req.location(), req.status());
        return trialMapper.toResponse(trialRepository.save(t));
    }

    public TrialResponse update(Long id, UpdateTrialRequest req) {
        Trial t = trialRepository.findById(id)
                .orElseThrow(() -> new TrialNotFoundException(id));

        t.setName(req.name());
        t.setLocation(req.location());
        t.setStatus(req.status());

        return trialMapper.toResponse(trialRepository.save(t));
    }
}
