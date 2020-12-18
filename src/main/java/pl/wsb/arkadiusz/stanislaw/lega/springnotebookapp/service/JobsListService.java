package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobsListRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;

import java.util.List;

@Service
public class JobsListService {

    private JobsListRepository jobsListRepository;

    @Autowired
    public JobsListService(JobsListRepository jobsListRepository) {
        this.jobsListRepository = jobsListRepository;
    }

    public List<JobsList> findListsByUserName(String userName) {
        return jobsListRepository.findListsByUserName(userName);
    }

    public JobsList saveJobsList(JobsList jobsList){
        return jobsListRepository.save(jobsList);
    }


}
