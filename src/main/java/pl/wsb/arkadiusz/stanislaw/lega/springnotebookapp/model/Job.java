package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "job_id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a job name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mmm:ss")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edited")
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mmm:ss")
    private Date edited;

    @ManyToOne
    @JoinColumn(name = "jobs_list_id", nullable = false)
    private JobsList parent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Job() {
        this.parent = new JobsList();
        this.owner = new User();
    }

    public Job(Integer id, @NotEmpty(message = "*Please provide a job name") String name, Date created, Date edited, JobsList parent, User owner) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.edited = edited;
        this.parent = parent;
        this.owner = owner;
    }
}
