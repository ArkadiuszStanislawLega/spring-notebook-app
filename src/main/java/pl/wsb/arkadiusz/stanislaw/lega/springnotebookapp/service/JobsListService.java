package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobsListRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobsListService {

    private JobsListRepository jobsListRepository;
    private UserRepository userRepository;


    @Autowired
    public JobsListService(JobsListRepository jobsListRepository, UserRepository userRepository) {
        this.jobsListRepository = jobsListRepository;
        this.userRepository = userRepository;
    }


    public List<JobsList> findListsByUserName(String owner) {
        return jobsListRepository.findListsByOwner(owner);
    }

    public JobsList saveJobsList(JobsList jobsList){
        return jobsListRepository.save(jobsList);
    }

    public JobsList find(Integer id) {return jobsListRepository.findById(id).orElse(null);}


}
