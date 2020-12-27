package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobsListRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.UserRepository;

import java.util.List;

@Service
public class JobService {
    private JobRepository jobRepository;
    private JobsListRepository parentRepository;
    private UserRepository ownerRepository;

    @Autowired
    public JobService(JobRepository jobRepository, JobsListRepository parentRepository, UserRepository ownerRepository) {
        this.jobRepository = jobRepository;
        this.parentRepository = parentRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Job> findJobsByUserName(String owner) {
        return jobRepository.findJobsByOwner(owner);
    }

    public List<Job> findJobsByParentId(Integer parentId) {
        return jobRepository.findJobsByParentId(parentId);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public void removeJob(Job job) {
        jobRepository.delete(job);
    }

    public Job find(Integer id) {
        return jobRepository.findById(id).orElse(null);
    }
}
