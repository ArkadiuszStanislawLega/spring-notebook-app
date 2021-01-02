package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.JobsList;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Role;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository.JobsListRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.service.JobsListService;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class JobsListServiceTest {

    @Autowired
    private JobsListService service;
    @MockBean
    private JobsListRepository repository;

    @Test
    void listAll_whenFound_thenReturnResult() {
        User user;
        JobsList jobsList1, jobsList2, jobsList3;
        Set<Role> userRole = new HashSet<>();
        userRole.add(new Role(1, "ADMIN"));

        Set<JobsList> userJobsList = new HashSet<>();

        user = new User(1,
                "userName",
                "e-mail.gmial.com",
                "zaq1@WSX",
                "name",
                "lastName",
                true,
                userRole,
                userJobsList);

        jobsList1 = new JobsList(
                1,
                "shopping list",
                new Date(),
                new Date(),
                user);

        jobsList2 = new JobsList(
                2,
                "Christmas quests",
                new Date(),
                new Date(),
                user);

        jobsList3 = new JobsList(
                3,
                "Task list from the boss",
                new Date(),
                new Date(),
                user);

        userJobsList.add(jobsList1);
        userJobsList.add(jobsList2);
        userJobsList.add(jobsList3);

        Iterable<JobsList> jobsLists = user.getJobsLists();

        Assertions.assertEquals(
                3,
                StreamSupport.stream(jobsLists.spliterator(), false).count()
        );
    }

    @Test
    void find_whenFound_thenReturnResult() {
        User user;
        Set<Role> userRole = new HashSet<>();
        userRole.add(new Role(1, "ADMIN"));

        Set<JobsList> userJobsList = new HashSet<>();

        user = new User(1,
                "userName",
                "e-mail.gmial.com",
                "zaq1@WSX",
                "name",
                "lastName",
                true,
                userRole,
                userJobsList);

// Setup our mock repository
        JobsList         jobsList = new JobsList(
                1,
                "shopping list",
                new Date(),
                new Date(),
                user);

        doReturn(Optional.of(jobsList)).when(repository).findById(1);
// Execute the service call
        JobsList result = service.find(1);
// Assert the response
        Assertions.assertTrue((result != null) );
        Assertions.assertSame(result, jobsList);
    }

}
