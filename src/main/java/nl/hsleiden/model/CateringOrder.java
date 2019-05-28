package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "catering_order")
public class CateringOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringorderid", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cateringid", nullable = false)
    private Catering catering;

    @Column(name = "datecateringoptions")
    @JsonProperty("datecateringoptions")
    private Date dateCateringOptions;

    @Column(name = "datecateringdefinite")
    @JsonProperty("datecateringdefinite")
    private Date dateCateringDefinite;

    @Column(name = "datecateringsend")
    @JsonProperty("datecateringsend")
    private Date dateCateringSend;

    @Column(name = "contactcateringoption")
    @Length(max = 255)
    @JsonProperty("contactcateringoption")
    private String contactCateringOption;

    @Column(name = "contactcateringdefinite")
    @Length(max = 255)
    @JsonProperty("contactcateringdefinite")
    private String contactCateringDefinite;

    @Column(name = "contactcateringsend")
    @Length(max = 255)
    @JsonProperty("contactcateringsend")
    private String contactCateringSend;

    @Column(name = "note")
    @JsonProperty("note")
    private String note;

    public CateringOrder() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Catering getCatering() {
        return catering;
    }

    public void setCatering(Catering catering) {
        this.catering = catering;
    }

    public Date getDateCateringOptions() {
        return dateCateringOptions;
    }

    public void setDateCateringOptions(Date dateCateringOptions) {
        this.dateCateringOptions = dateCateringOptions;
    }

    public Date getDateCateringDefinite() {
        return dateCateringDefinite;
    }

    public void setDateCateringDefinite(Date dateCateringDefinite) {
        this.dateCateringDefinite = dateCateringDefinite;
    }

    public Date getDateCateringSend() {
        return dateCateringSend;
    }

    public void setDateCateringSend(Date dateCateringSend) {
        this.dateCateringSend = dateCateringSend;
    }

    public String getContactCateringOption() {
        return contactCateringOption;
    }

    public void setContactCateringOption(String contactCateringOption) {
        this.contactCateringOption = contactCateringOption;
    }

    public String getContactCateringDefinite() {
        return contactCateringDefinite;
    }

    public void setContactCateringDefinite(String contactCateringDefinite) {
        this.contactCateringDefinite = contactCateringDefinite;
    }

    public String getContactCateringSend() {
        return contactCateringSend;
    }

    public void setContactCateringSend(String contactCateringSend) {
        this.contactCateringSend = contactCateringSend;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
