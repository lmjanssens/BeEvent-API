package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    @JsonProperty("supplierid")
    private Long id;

    @NotNull
    @Length(max = 100)
    @Column(name = "suppliername")
    @JsonProperty("name")
    private String name;

    @NotNull
    @Column(name = "contactperson")
    @JsonProperty("contact_person")
    private String contactPerson;

    @NotNull
    @Column(name = "supervisor")
    @JsonProperty("supervisor")
    private String supervisor;

    @Length(max = 150)
    @Column(name = "website")
    @JsonProperty("website")
    private String website;

    @Column(name = "note")
    @JsonProperty("note")
    private String note;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @OneToMany(mappedBy = "supplier")
    @JsonProperty("email_addresses")
    private Set<SupplierEmail> emails;

    @OneToMany(mappedBy = "supplier")
    @JsonProperty("phone_numbers")
    private Set<SupplierPhone> phones;

    @OneToMany(mappedBy = "supplier")
    @JsonProperty("contracts")
    private Set<SupplierContract> contracts;

    @OneToMany(mappedBy = "supplier")
    @JsonProperty("addresses")
    private Set<SupplierAddress> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<SupplierAddress> getAddresses() { return addresses; }

    public void setAddresses(Set<SupplierAddress> addresses) { this.addresses = addresses; }
}
