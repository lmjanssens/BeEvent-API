package nl.hsleiden.repository;

import nl.hsleiden.model.EmailText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailTextRepository extends JpaRepository<EmailText, Long> {
    @Override
    List<EmailText> findAll();
}
