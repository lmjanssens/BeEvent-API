package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "quotation")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotationnumber", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long quotationNumber;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    @JsonBackReference("quotationOrderRef")
    @JsonView(View.Public.class)
    private Order order;

    @NotNull
    @Column(name = "datequotation")
    @JsonProperty("datequotation")
    @JsonView(View.Public.class)
    private Date dateQuotation;

    @NotNull
    @Column(name = "bankaccount")
    @Length(max = 255)
    @JsonProperty("bankaccount")
    @JsonView(View.Public.class)
    private String bankAccount;

    @NotNull
    @Column(name = "pricebtw")
    @JsonProperty("pricebtw")
    @JsonView(View.Public.class)
    private double priceBtw;

    @NotNull
    @Column(name = "pricepp")
    @JsonProperty("pricepp")
    @JsonView(View.Public.class)
    private double pricePp;

    public Long getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(Long quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public Date getDateQuotation() {
        return dateQuotation;
    }

    public void setDateQuotation(Date dateQuotation) {
        this.dateQuotation = dateQuotation;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getPriceBtw() {
        return priceBtw;
    }

    public void setPriceBtw(double priceBtw) {
        this.priceBtw = priceBtw;
    }

    public double getPricePp() {
        return pricePp;
    }

    public void setPricePp(double pricePp) {
        this.pricePp = pricePp;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
