package ee.menken.trial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "trial")
public class Trial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 200)
    @Column(length = 200)
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrialStatus status;


    public Trial(String name, String location, TrialStatus status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }

    protected Trial() {
    }


    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public TrialStatus getStatus() { return status; }
    public void setStatus(TrialStatus status) { this.status = status; }
}
