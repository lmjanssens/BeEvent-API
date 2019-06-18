package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoicenumber", columnDefinition = "serial")
    @JsonProperty("invoiceNumber")
    @JsonView(View.Public.class)
    private Long invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    @JsonBackReference("invoiceOrderRef")
    private Order order;

    @Column(name = "dateinvoice")
    @JsonProperty("dateinvoice")
    @JsonView(View.Public.class)
    private Date dateInvoice;

    @Column(name = "paymentextras")
    @Length(max = 255)
    @JsonProperty("paymentextras")
    @JsonView(View.Public.class)
    private String paymentExtras;

    @Column(name = "pricepp")
    @JsonProperty("pricepp")
    @JsonView(View.Public.class)
    private int pricePp;

    @Column(name = "pricebtw")
    @JsonProperty("pricebtw")
    @JsonView(View.Public.class)
    private int priceBtw;

    @Column(name = "othercosts")
    @JsonProperty("othercosts")
    @JsonView(View.Public.class)
    private double otherCosts;

    @Column(name = "othercostsbtw")
    @JsonProperty("othercostsbtw")
    @JsonView(View.Public.class)
    private double otherCostsBtw;

    @Column(name = "tobepaid")
    @JsonProperty("tobepaid")
    @JsonView(View.Public.class)
    private double toBePaid;

    @Column(name = "paid")
    @JsonProperty("paid")
    @JsonView(View.Public.class)
    private double paid;

    @Column(name = "datepartpaid")
    @JsonProperty("datepartpaid")
    @JsonView(View.Public.class)
    private Date datePartPaid;

    @Column(name = "datefullpaid")
    @JsonProperty("datefullpaid")
    @JsonView(View.Public.class)
    private Date dateFullPaid;

    @Column(name = "bankaccount")
    @Length(max = 255)
    @JsonProperty("bankaccount")
    @JsonView(View.Public.class)
    private String bankAccount;

    @Column(name = "dateinvoicemailsend")
    @JsonProperty("dateinvoicemailsend")
    @JsonView(View.Public.class)
    private Date dateInvoiceMailSent;

    @Column(name = "excludefrominvoicealert")
    @JsonProperty("excludefrominvoicealert")
    @JsonView(View.Public.class)
    private boolean excludeFromInvoiceAlert;

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaymentExtras() {
        return paymentExtras;
    }

    public void setPaymentExtras(String paymentExtras) {
        this.paymentExtras = paymentExtras;
    }

    public int getPricePp() {
        return pricePp;
    }

    public void setPricePp(int pricePp) {
        this.pricePp = pricePp;
    }

    public int getPriceBtw() {
        return priceBtw;
    }

    public void setPriceBtw(int priceBtw) {
        this.priceBtw = priceBtw;
    }

    public double getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(double otherCosts) {
        this.otherCosts = otherCosts;
    }

    public double getOtherCostsBtw() {
        return otherCostsBtw;
    }

    public void setOtherCostsBtw(double otherCostsBtw) {
        this.otherCostsBtw = otherCostsBtw;
    }

    public double getToBePaid() {
        return toBePaid;
    }

    public void setToBePaid(double toBePaid) {
        this.toBePaid = toBePaid;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean getExcludeFromInvoiceAlert() {
        return excludeFromInvoiceAlert;
    }

    public void setExcludeFromInvoiceAlert(boolean excludeFromInvoiceAlert) {
        this.excludeFromInvoiceAlert = excludeFromInvoiceAlert;
    }

    public Date getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(Date dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public Date getDatePartPaid() {
        return datePartPaid;
    }

    public void setDatePartPaid(Date datePartPaid) {
        this.datePartPaid = datePartPaid;
    }

    public Date getDateFullPaid() {
        return dateFullPaid;
    }

    public void setDateFullPaid(Date dateFullPaid) {
        this.dateFullPaid = dateFullPaid;
    }

    public Date getDateInvoiceMailSent() {
        return dateInvoiceMailSent;
    }

    public void setDateInvoiceMailSent(Date dateInvoiceMailSent) {
        this.dateInvoiceMailSent = dateInvoiceMailSent;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
