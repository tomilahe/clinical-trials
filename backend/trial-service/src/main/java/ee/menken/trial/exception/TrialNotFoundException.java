package ee.menken.trial.exception;

public class TrialNotFoundException extends RuntimeException {
    public TrialNotFoundException(Long id) {
        super("Trial not found: " + id);
    }
}

