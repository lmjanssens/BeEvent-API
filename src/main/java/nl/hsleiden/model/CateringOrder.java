package nl.hsleiden.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "catering_order")
public class CateringOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateringorderid", columnDefinition = "serial")
    private Long id;

    @Column(name = "orderid")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "cateringid", nullable = false)
    private Catering catering;

    @NotNull
    @Column(name = "datecateringoptions")
    private String dateCateringOptions;

    @NotNull
    @Column(name = "datecateringdefinite")
    private String dateCateringDefinite;

    @NotNull
    @Column(name = "datecateringsent")
    private String dateCateringSent;

    @NotNull
    @Column(name = "contactcateringoption")
    private String contactCateringOption;

    @NotNull
    @Column(name = "contactcateringdefinite")
    private String contactCateringDefinite;

    @NotNull
    @Column(name = "contactcateringsent")
    private String contactCateringSent;

    @Column(name = "note")
    private String note;

}
