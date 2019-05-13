package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "Catering_Image")
public class CateringImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringid", columnDefinition = "serial")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "contractid")
    private Long contractId;

    public CateringImage(String image, Long contractId) {
        this.image = image;
        this.contractId = contractId;
    }

    public CateringImage() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
