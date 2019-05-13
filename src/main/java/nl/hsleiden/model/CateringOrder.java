package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "cateringorder")
public class CateringOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringorderid", columnDefinition = "serial")
    private Long id;

    @Column(name = "orderid")
    private Long orderId;

    @Column(name = "cateringid")
    private Long cateringId;

    @Column(name = "datecateringoptions")
    private String dateCateringOptions;

    @Column(name = "datecateringdefinite")
    private String dateCateringDefinite;

    @Column(name = "datecateringsent")
    private String dateCateringSent;

    @Column(name = "contactcateringoption")
    private String contactCateringOption;

    @Column(name = "contactcateringdefinite")
    private String contactCateringDefinite;

    @Column(name = "contactcateringsent")
    private String contactCateringSent;

    @Column(name = "note")
    private String note;

    public CateringOrder(Long orderId, Long cateringId, String dateCateringOptions, String dateCateringDefinite,
                         String dateCateringSent, String contactCateringOption, String contactCateringDefinite,
                         String contactCateringSent, String note) {
        this.orderId = orderId;
        this.cateringId = cateringId;
        this.dateCateringOptions = dateCateringOptions;
        this.dateCateringDefinite = dateCateringDefinite;
        this.dateCateringSent = dateCateringSent;
        this.contactCateringOption = contactCateringOption;
        this.contactCateringDefinite = contactCateringDefinite;
        this.contactCateringSent = contactCateringSent;
        this.note = note;
    }

    public CateringOrder() {

    }

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

    public String getDateCateringOptions() {
        return dateCateringOptions;
    }

    public void setDateCateringOptions(String dateCateringOptions) {
        this.dateCateringOptions = dateCateringOptions;
    }

    public String getDateCateringDefinite() {
        return dateCateringDefinite;
    }

    public void setDateCateringDefinite(String dateCateringDefinite) { this.dateCateringDefinite = dateCateringDefinite; }

    public String getDateCateringSent() {
        return dateCateringSent;
    }

    public void setDateCateringSent(String dateCateringSent) {
        this.dateCateringSent = dateCateringSent;
    }

    public String getContactCateringOption() {
        return contactCateringOption;
    }

    public void setContactCateringOption(String contactCateringOption) { this.contactCateringOption = contactCateringOption; }

    public String getContactCateringDefinite() {
        return contactCateringDefinite;
    }

    public void setContactCateringDefinite(String contactCateringDefinite) { this.contactCateringDefinite = contactCateringDefinite; }

    public String getContactCateringSent() {
        return contactCateringSent;
    }

    public void setContactCateringSent(String contactCateringSent) {
        this.contactCateringSent = contactCateringSent;
    }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }
}
