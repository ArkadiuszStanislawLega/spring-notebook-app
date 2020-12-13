package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {

}