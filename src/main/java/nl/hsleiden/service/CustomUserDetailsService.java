package nl.hsleiden.service;

import nl.hsleiden.auth.Role;
import nl.hsleiden.model.Employee;
import nl.hsleiden.model.Instructor;
import nl.hsleiden.model.User;
import nl.hsleiden.repository.EmployeeRepository;
import nl.hsleiden.repository.InstructorRepository;
import nl.hsleiden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException(username);

        Employee employee = employeeRepository.findByUserId(user.getId());
        Instructor instructor = instructorRepository.findByUserId(user.getId());

        if (this.isAdmin(employee))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN));
        else if (this.isEmployee(employee))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.EMPLOYEE));
        else if (this.isInstructor(instructor))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.INSTRUCTOR));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public boolean isEmployee(Employee employee) {
        return employee != null;
    }

    public boolean isAdmin(Employee employee) {
        return this.isEmployee(employee) && employee.getUser().getId() == 1;
    }

    public boolean isInstructor(Instructor instructor) {
        return instructor != null;
    }
}
