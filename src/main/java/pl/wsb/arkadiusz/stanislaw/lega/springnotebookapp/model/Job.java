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

    @Column(name = "title")
    @NotEmpty(message = "*Please provide a job name")
    private String title;

    @Column(name = "content")
    private String content = "";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edited")
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Job(Integer id, @NotEmpty(message = "*Please provide a job name") String title, String content, Date created, Date edited, JobsList parent) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.edited = edited;
        this.parent = parent;
    }

    public String toString(){
        return this.id + " " + this.title + " " + this.content + " Parent: " + this.parent.getId();
    }
}
