package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}
