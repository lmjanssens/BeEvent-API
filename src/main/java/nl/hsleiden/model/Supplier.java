package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierID", columnDefinition = "serial")
    private Long id;

    @NotNull
    @Length(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Length(max = 20)
    @Column(name = "infix")
    private String infix;

    @NotNull
    @Length(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "contact_person")
    private String contactPerson;

    @NotNull
    @Column(name = "contact_person")
    private String supervisor;

    @Length(max = 150)
    @Column(name = "website")
    private String website;

    @Column(name = "note")
    private String note;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierEmail> emails;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierPhone> phones;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierContract> contracts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<SupplierEmail> getEmails() {
        return emails;
    }

    public void setEmails(Set<SupplierEmail> emails) {
        this.emails = emails;
    }

    public Set<SupplierPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<SupplierPhone> phones) {
        this.phones = phones;
    }

    public Set<SupplierContract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<SupplierContract> contracts) {
        this.contracts = contracts;
    }
}
