package ru.sportlive.mvp.dto.input;

import javax.xml.crypto.Data;


public class PaymentConformationDTO {

    private String notification_type;
    private String operation_id;
    private Double amount;
    private Double withdraw_amount;
    private String currency;
    private Data data;
    private String sender;
    private Boolean codepro;
    private String label;
    private String sha1_hash;
    private Boolean unaccepted;

    public PaymentConformationDTO(String notification_type, String operation_id, Double amount, Double withdraw_amount, String currency, Data data, String sender, Boolean codepro, String label, String sha1_hash, Boolean unaccepted) {
        this.notification_type = notification_type;
        this.operation_id = operation_id;
        this.amount = amount;
        this.withdraw_amount = withdraw_amount;
        this.currency = currency;
        this.data = data;
        this.sender = sender;
        this.codepro = codepro;
        this.label = label;
        this.sha1_hash = sha1_hash;
        this.unaccepted = unaccepted;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(Double withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getCodepro() {
        return codepro;
    }

    public void setCodepro(Boolean codepro) {
        this.codepro = codepro;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSha1_hash() {
        return sha1_hash;
    }

    public void setSha1_hash(String sha1_hash) {
        this.sha1_hash = sha1_hash;
    }

    public Boolean getUnaccepted() {
        return unaccepted;
    }

    public void setUnaccepted(Boolean unaccepted) {
        this.unaccepted = unaccepted;
    }
}
