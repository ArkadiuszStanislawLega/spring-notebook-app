package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.comparators.JobComparatorByCreateDate;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.comparators.JobListsComparatorsByCreateDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String userName;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "owner")
    private Set<JobsList> jobsLists;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addJobsList(JobsList jobsList){
        this.jobsLists.add(jobsList);
    }

    public void removeJobsList(JobsList jobsList){
        this.jobsLists.remove(jobsList);
    }

    public Set<JobsList> getJobsLists() {
        return this.jobsLists;
    }

    /***
     * Sorting JobsList by created date, newest at the beginning of list.
     * @return List with sorted JobsList by created date.
     */
    public List<JobsList> getSortedByDateJobsList(){
        List<JobsList> sortedList = new ArrayList<>();
        List<JobsList> reverseSortedList = new ArrayList<>();

        for (JobsList jobList: this.jobsLists){
            sortedList.add(jobList);
        }

        Collections.sort(sortedList, new JobListsComparatorsByCreateDate());

        for (int i=sortedList.size()-1; i>=0; i--){
            reverseSortedList.add(sortedList.get(i));
        }

        return reverseSortedList;
    }

    /**
     * Sorting Jobs by created date, newest at the beginning of list.
     * @return List with sorted Jobs by created date.
     */
    public List<Job> getSortedByDateJobs()
    {
        List<Job> jobs = new ArrayList<>();
        List<Job> reverseSortedJobs = new ArrayList<>();

        for (JobsList parent : this.getJobsLists()) {
            jobs.addAll(parent.getJobsList());
        }

        jobs.sort(new JobComparatorByCreateDate());

        for (int i=jobs.size()-1; i>=0; i--){
            reverseSortedJobs.add(jobs.get(i));
        }
        return reverseSortedJobs;
    }
}

