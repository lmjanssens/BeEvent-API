package nl.hsleiden.repository;

import nl.hsleiden.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    List<UserAction> findUserActionById(Long userActionId);

    @Override
    List<UserAction> findAll();
}
