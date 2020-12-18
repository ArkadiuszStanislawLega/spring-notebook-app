package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;

import java.util.List;

public interface JobsListRepository extends JpaRepository<JobsList, Integer> {
    List<JobsList> findListsByOwner(String owner);
}
