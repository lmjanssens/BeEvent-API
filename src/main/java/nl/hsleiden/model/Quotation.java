package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "quotation")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotationnumber", columnDefinition = "serial")
    private Long quotationNumber;

    @Column(name = "orderid")
    private long orderId;

    @Column(name = "datequotation")
    private String dateQuotation;

    @Column(name = "bankaccount")
    private String bankAccount;

    @Column(name = "pricebtw")
    private int priceBtw;

    @Column(name = "pricepp")
    private int pricePp;

    public Quotation(long orderId, String dateQuotation, String bankAccount, int priceBtw, int pricePp) {
        this.orderId = orderId;
        this.dateQuotation = dateQuotation;
        this.bankAccount = bankAccount;
        this.priceBtw = priceBtw;
        this.pricePp = pricePp;
    }

    public Quotation() {

    }

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

    public String getDateQuotation() {
        return dateQuotation;
    }

    public void setDateQuotation(String dateQuotation) {
        this.dateQuotation = dateQuotation;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int getPriceBtw() {
        return priceBtw;
    }

    public void setPriceBtw(int priceBtw) {
        this.priceBtw = priceBtw;
    }

    public int getPricePp() {
        return pricePp;
    }

    public void setPricePp(int pricePp) {
        this.pricePp = pricePp;
    }
}
