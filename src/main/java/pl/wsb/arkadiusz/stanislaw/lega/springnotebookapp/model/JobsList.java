package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="jobs_lists")
public class JobsList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "users_lists_id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a jobs list name")
    private String name;

    @Column(name = "created")
    private Date created;

    @Column(name = "edited")
    private Date edited;

    @ManyToMany
    Set<User> owner;

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

    public Set<User> getOwner() {
        return owner;
    }

    @Autowired(required=true)
    public void setOwner(Set<User> owner) {
        this.owner = owner;
    }

    public void addOwner(User user){
        this.owner.add(user);
    }

    public void removeUser(User user){
        this.owner.remove(user);
    }

    public JobsList() {
        this.owner = new HashSet<>();
    }

}
