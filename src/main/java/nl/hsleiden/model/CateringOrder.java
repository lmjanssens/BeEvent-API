package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "catering_order")
public class CateringOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringorderid", columnDefinition = "serial")
    private Long id;

    @Column(name = "orderid")
    @JsonProperty("orderid")
    @NotNull
    private Long orderId;

    @Column(name = "cateringid")
    @JsonProperty("cateringid")
    @NotNull
    private Long cateringId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCateringId() {
        return cateringId;
    }

    public void setCateringId(Long cateringId) {
        this.cateringId = cateringId;
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
}
