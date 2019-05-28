package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Datamodel class CateringImage
 * @author Robin Silverio
 */

@Entity
@Table(name = "catering_image")
public class CateringImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringimageid")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cateringid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Catering catering;

    @NotNull
    @Length(max = 255)
    @Column(name = "image")
    private String image;

    public CateringImage(Catering catering, @NotNull @Length(max = 255) String image) {
        this.catering = catering;
        this.image = image;
    }

    public CateringImage() {}

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
