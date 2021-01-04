package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Job;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobRepository;

import java.util.List;

@Service
public class JobService {
    private final JobRepository JOB_REPOSITORY;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.JOB_REPOSITORY = jobRepository;
    }

    public List<Job> findJobsByParentId(Integer parentId) {
        return this.JOB_REPOSITORY.findJobsByParentId(parentId);
    }

    public Job saveJob(Job job) {
        return this.JOB_REPOSITORY.save(job);
    }

    public void removeJob(Job job) {
        this.JOB_REPOSITORY.delete(job);
    }

    public Job find(Integer id) {
        return this.JOB_REPOSITORY.findById(id).orElse(null);
    }
}
