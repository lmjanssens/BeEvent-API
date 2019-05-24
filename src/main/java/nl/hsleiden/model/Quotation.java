package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long quotationNumber;

    @Column(name = "orderid")
    @JsonProperty("orderid")
    @NotNull
    private long orderId;

    @Column(name = "datequotation")
    @JsonProperty("datequotation")
    @NotNull
    private Date dateQuotation;

    @Column(name = "bankaccount")
    @Length(max = 255)
    @JsonProperty("bankaccount")
    @NotNull
    private String bankAccount;

    @Column(name = "pricebtw")
    @JsonProperty("pricebtw")
    @NotNull
    private double priceBtw;

    @Column(name = "pricepp")
    @JsonProperty("pricepp")
    @NotNull
    private double pricePp;

    public Long getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(Long quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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
}
