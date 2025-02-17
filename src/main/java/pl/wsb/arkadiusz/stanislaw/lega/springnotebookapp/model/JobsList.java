package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.comparators.JobComparatorByCreateDate;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.statics.Setup;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name="jobs_lists")
public class JobsList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "jobs_list_id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a jobs list name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = Setup.DATE_TIME_FORMAT)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edited")
    @DateTimeFormat(pattern = Setup.DATE_TIME_FORMAT)
    private Date edited;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToMany(targetEntity = Job.class,
                mappedBy = "parent",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Set<Job> jobsList;

    public void addJob(Job job){
        if (job != null)
            this.jobsList.add(job);
    }

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

    public User getOwner() {
        return owner;
    }

    @Autowired(required=true)
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Job> getJobsList() {
        return jobsList;
    }

    public void setJobsList(Set<Job> jobsList) {
        this.jobsList = jobsList;
    }

    public JobsList(){
        this.owner = new User();
        this.jobsList = new HashSet<>();
    }

    public JobsList(Integer id, @NotEmpty(message = "*Please provide a jobs list name") String name, Date created, Date edited, User owner, Set<Job> jobsList) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.edited = edited;
        this.owner = owner;
        this.jobsList = jobsList;
    }

    public JobsList(Integer id, @NotEmpty(message = "*Please provide a jobs list name") String name, Date created, Date edited, User owner) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.edited = edited;
        this.owner = owner;
    }

    public String toString(){
        return this.id + " " + this.name + " id_owner: " + this.owner.getId();
    }


    /**
     * Sorting Jobs by created date, newest at the beginning of list.
     * @return List with sorted Jobs by created date.
     */
    public List<Job> getSortedByDateJobs()
    {
        List<Job> jobs = new ArrayList<>();
        List<Job> reverseSortedJobs = new ArrayList<>();

        for (Job job : this.jobsList) {
                jobs.add(job);
        }

        Collections.sort(jobs, new JobComparatorByCreateDate());

        for (int i=jobs.size()-1; i>=0; i--){
            reverseSortedJobs.add(jobs.get(i));
        }
        return reverseSortedJobs;
    }
}
