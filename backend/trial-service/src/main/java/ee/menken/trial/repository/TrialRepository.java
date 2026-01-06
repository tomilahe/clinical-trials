package ee.menken.trial.repository;

import ee.menken.trial.entity.Trial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrialRepository extends JpaRepository<Trial, Long> {
}
