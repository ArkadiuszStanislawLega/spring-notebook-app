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

    @ManyToOne(targetEntity = JobsList.class)
    @JoinColumn(name = "jobs_list_id")
    private JobsList parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public JobsList getParent() {
        return parent;
    }

    public void setParent(JobsList parent) {
        this.parent = parent;
    }

    public Job() {
        this.parent = new JobsList();
    }

    public Job(Integer id, @NotEmpty(message = "*Please provide a job name") String name, Date created, Date edited, JobsList parent) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.edited = edited;
        this.parent = parent;
    }
}
