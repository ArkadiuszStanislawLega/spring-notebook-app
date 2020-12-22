package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;

import java.util.List;

public interface JobsListRepository extends CrudRepository<JobsList, Integer> {
    List<JobsList> findListsByOwner(String owner);
}
