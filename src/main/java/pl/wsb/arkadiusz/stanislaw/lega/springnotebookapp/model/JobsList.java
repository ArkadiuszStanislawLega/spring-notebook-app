package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="jobs_lists")
public class JobsList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "users_lists_id")
    private Integer id;

    @Column(name = "user_name")
    @NotEmpty(message = "*Please provide a jobs list name")
    private String listName;

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

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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

    public void setOwner(Set<User> owner) {
        this.owner = owner;
    }
}
