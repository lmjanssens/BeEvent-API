package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "customerphone")
public class CustomerPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", columnDefinition = "serial")
    private Long id;

    @Column(name = "phone")
    private String phone;

    public CustomerPhone(String phone) { this.phone = phone; }

    public CustomerPhone() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
