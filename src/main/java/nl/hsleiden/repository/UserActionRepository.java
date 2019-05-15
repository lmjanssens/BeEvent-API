package nl.hsleiden.repository;

import nl.hsleiden.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    List<UserAction> findUserActionById(Long userActionId);

    @Override
    List<UserAction> findAll();
}
