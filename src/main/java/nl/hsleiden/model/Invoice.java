package nl.hsleiden.model;

import javax.persistence.*;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoicenumber", columnDefinition = "serial")
    private Long invoiceNumber;

    @Column(name = "orderid")
    private long orderId;

    @Column(name = "dateinvoice")
    private String dateInvoice;

    @Column(name = "paymentextras")
    private String paymentExtras;

    @Column(name = "pricepp")
    private int pricePp;

    @Column(name = "pricebtw")
    private int priceBtw;

    @Column(name = "othercosts")
    private int otherCosts;

    @Column(name = "othercostsbtw")
    private int otherCostsBtw;

    @Column(name = "tobepaid")
    private int toBePaid;

    @Column(name = "paid")
    private int paid;

    @Column(name = "datepartpaid")
    private String datePartPaid;

    @Column(name = "datefullpaid")
    private String dateFullPaid;

    @Column(name = "bankaccount")
    private String bankAccount;

    @Column(name = "dateinvoicemailsent")
    private String dateInvoiceMailSent;

    @Column(name = "excludefrominvoicealert")
    private String excludeFromInvoiceAlert;

    public Invoice(long orderId, String dateInvoice, String paymentExtras, int pricePp, int priceBtw, int otherCosts,
                   int otherCostsBtw, int toBePaid, int paid, String datePartPaid, String dateFullPaid,
                   String bankAccount, String dateInvoiceMailSent, String excludeFromInvoiceAlert) {
        this.orderId = orderId;
        this.dateInvoice = dateInvoice;
        this.paymentExtras = paymentExtras;
        this.pricePp = pricePp;
        this.priceBtw = priceBtw;
        this.otherCosts = otherCosts;
        this.otherCostsBtw = otherCostsBtw;
        this.toBePaid = toBePaid;
        this.paid = paid;
        this.datePartPaid = datePartPaid;
        this.dateFullPaid = dateFullPaid;
        this.bankAccount = bankAccount;
        this.dateInvoiceMailSent = dateInvoiceMailSent;
        this.excludeFromInvoiceAlert = excludeFromInvoiceAlert;
    }

    public Invoice() {

    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(String dateInvoice) {
        this.dateInvoice = dateInvoice;
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

    public int getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(int otherCosts) {
        this.otherCosts = otherCosts;
    }

    public int getOtherCostsBtw() {
        return otherCostsBtw;
    }

    public void setOtherCostsBtw(int otherCostsBtw) {
        this.otherCostsBtw = otherCostsBtw;
    }

    public int getToBePaid() {
        return toBePaid;
    }

    public void setToBePaid(int toBePaid) {
        this.toBePaid = toBePaid;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getDatePartPaid() {
        return datePartPaid;
    }

    public void setDatePartPaid(String datePartPaid) {
        this.datePartPaid = datePartPaid;
    }

    public String getDateFullPaid() {
        return dateFullPaid;
    }

    public void setDateFullPaid(String dateFullPaid) {
        this.dateFullPaid = dateFullPaid;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDateInvoiceMailSent() {
        return dateInvoiceMailSent;
    }

    public void setDateInvoiceMailSent(String dateInvoiceMailSent) {
        this.dateInvoiceMailSent = dateInvoiceMailSent;
    }

    public String getExcludeFromInvoiceAlert() {
        return excludeFromInvoiceAlert;
    }

    public void setExcludeFromInvoiceAlert(String excludeFromInvoiceAlert) {
        this.excludeFromInvoiceAlert = excludeFromInvoiceAlert;
    }
}
