package nl.hsleiden.util;

import nl.hsleiden.mock.*;
import nl.hsleiden.model.Employee;
import nl.hsleiden.model.EmployeeEmail;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.EmployeeRepository;
import nl.hsleiden.repository.InstructorRepository;
import nl.hsleiden.repository.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class DatabaseTestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    private ArrayList<User> users = new ArrayList<>(
            Arrays.asList(
                    new MockUserAdministrator(), new MockUserEmployee()
            )
    );

    private ArrayList<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new MockEmployeeAdministrator(), new MockEmployee()
            )
    );

    private Instructor instructor = new MockInstructor();

    public void setupUsers() {
        setupEmployees();
        setupInstructor();
    }

    private void setupEmployees() {
        for (int i = 0; i < employees.size(); i++)
            employees.get(i).setUser(users.get(i));

        employeeRepository.saveAll(employees);
    }

    private void setupInstructor() {
        instructor.setUser(new MockUserInstructor());
        instructorRepository.save(instructor);
    }
}
