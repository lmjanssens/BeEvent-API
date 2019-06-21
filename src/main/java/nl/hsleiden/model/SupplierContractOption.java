package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supplier_contract_option")
public class SupplierContractOption {



    public Long getOptionid() {
        return optionid;
    }

    public void setOptionid(Long optionid) {
        this.optionid = optionid;
    }

//    public long getContractid() {
//        return contractid;
//    }
//
//    public void setContractid(long contractid) {
//        this.contractid = contractid;
//    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public SupplierContract getContract() {
        return contract;
    }

    public void setContract(SupplierContract contract) {
        this.contract = contract;
    }

    @ManyToOne
    @JoinColumn(name = "contractid", nullable = false)
    @JsonBackReference("optionContractRef")
    public SupplierContract contract;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long optionid;

//    @JoinColumn(name = "contractid", nullable = false)
//    @JsonBackReference("contractid")
//    private Long contractid;

    @NotNull
    @Column(name = "option")
    @JsonProperty("option")
    @JsonView(View.Public.class)
    private String option;
}


