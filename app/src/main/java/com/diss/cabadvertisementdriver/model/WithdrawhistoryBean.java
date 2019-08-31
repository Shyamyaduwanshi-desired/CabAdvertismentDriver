package com.diss.cabadvertisementdriver.model;

public class WithdrawhistoryBean {
    String  ID,user_id,amount_request,amount_paid,request_status,requested_on,remark,trans_id,accepted_on,payment_on;
    String name,account_number,ifsc_code,description;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAmount_request() {
        return amount_request;
    }

    public void setAmount_request(String amount_request) {
        this.amount_request = amount_request;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getRequested_on() {
        return requested_on;
    }

    public void setRequested_on(String requested_on) {
        this.requested_on = requested_on;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getAccepted_on() {
        return accepted_on;
    }

    public void setAccepted_on(String accepted_on) {
        this.accepted_on = accepted_on;
    }

    public String getPayment_on() {
        return payment_on;
    }

    public void setPayment_on(String payment_on) {
        this.payment_on = payment_on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
