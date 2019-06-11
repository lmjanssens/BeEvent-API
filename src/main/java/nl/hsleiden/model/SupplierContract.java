package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "supplier_contract")
public class SupplierContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractid", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierid", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    @NotNull
    @Column(name = "type_contract")
    @JsonProperty("type")
    private boolean typeContract;

    @Length(max = 20)
    @Column(name = "contract_title")
    @JsonProperty("title")
    private String title;

    @NotNull
    @Length(max = 100)
    @Column(name = "contract_description")
    @JsonProperty("description")
    private String description;

    @NotNull
    @Column(name = "contract_incl_btw")
    @JsonProperty("inclubtw")
    private String incluBtw;

    @Min(0)
    @Max(100)
    @Column(name = "contract_btw_percentage")
    @JsonProperty("btw_percentage")
    private int percentage;

    @Column(name = "contract_preconditions")
    @JsonProperty("pre_conditions")
    private String preconditions;

    @Column(name = "contract_insurance")
    @JsonProperty("insurance")
    private String insurance;

    @Column(name = "contract_responsibility")
    @JsonProperty("responsibility")
    private String responsibility;

    @Column(name = "contract_extras")
    @JsonProperty("extras")
    private String extras;

    public Long getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isTypeContract() {
        return typeContract;
    }

    public void setTypeContract(boolean typeContract) {
        this.typeContract = typeContract;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncluBtw() {
        return incluBtw;
    }

    public void setIncluBtw(String incluBtw) {
        this.incluBtw = incluBtw;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object object) {
        SupplierContract supplierContract = (SupplierContract) object;
        return (
                this.typeContract == supplierContract.isTypeContract() &&
                        this.title.equals(supplierContract.getTitle()) &&
                        this.description.equals(supplierContract.getDescription()) &&
                        this.incluBtw.equals(supplierContract.getIncluBtw()) &&
                        this.percentage == supplierContract.getPercentage() &&
                        this.preconditions.equals(supplierContract.getPreconditions()) &&
                        this.insurance.equals(supplierContract.getInsurance()) &&
                        this.responsibility.equals(supplierContract.getResponsibility()) &&
                        this.extras.equals(supplierContract.getExtras())
        );
    }
}
