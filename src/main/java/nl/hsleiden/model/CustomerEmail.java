package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "customeremail")
public class CustomerEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", columnDefinition = "serial")
    private Long id;

    @Column(name = "email")
    private String email;

    public CustomerEmail(String email) { this.email = email; }

    public CustomerEmail() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
