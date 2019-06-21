package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "catering_order_option")
public class CateringOrderOption {

    public long getOptionid() {
        return optionid;
    }

    public void setOptionid(long optionid) {
        this.optionid = optionid;
    }

//    public long getCateringorderid() {
//        return cateringorderid;
//    }
//
//    public void setCateringorderid(long cateringorderid) {
//        this.cateringorderid = cateringorderid;
//    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private long optionid;
//
//    @JoinColumn(name = "cateringorderid", nullable = false)
//    @JsonBackReference("cateringorderid")
//    private long cateringorderid;

    public CateringOrder getCateringorder() {
        return cateringorder;
    }

    public void setCateringorder(CateringOrder cateringorder) {
        this.cateringorder = cateringorder;
    }
    @ManyToOne
    @JoinColumn(name = "cateringorder", nullable = false, insertable = false, updatable = false)
    @JsonBackReference("cateringorder")
    private CateringOrder cateringorder;

    @NotNull
    @Column(name = "option")
    @JsonProperty("option")
    @JsonView(View.Public.class)
    private String option;
}

