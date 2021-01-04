package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobsListRepository;

import java.util.List;

@Service
public class JobsListService {

    private final JobsListRepository JOBS_LIST_REPOSITORY;


    @Autowired
    public JobsListService(JobsListRepository jobsListRepository) {
        this.JOBS_LIST_REPOSITORY = jobsListRepository;
    }


    public List<JobsList> findListsByUserName(String owner) {
        return JOBS_LIST_REPOSITORY.findListsByOwner(owner);
    }

    public JobsList saveJobsList(JobsList jobsList){
        return JOBS_LIST_REPOSITORY.save(jobsList);
    }

    public void removeJobsList(JobsList jobsList){
        JOBS_LIST_REPOSITORY.delete(jobsList);
    }

    public JobsList find(Integer id) {return JOBS_LIST_REPOSITORY.findById(id).orElse(null);}

    public Iterable<JobsList> listAll() { return JOBS_LIST_REPOSITORY.findAll();}


}
