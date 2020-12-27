package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Integer> {
    List<Job> findJobsByOwner(String owner);
    List<Job> findJobsByParentId(Integer parentId);
}
