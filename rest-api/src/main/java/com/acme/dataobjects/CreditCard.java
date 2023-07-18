package com.acme.dataobjects;

import java.math.BigInteger;

public class CreditCard {
    private  double amount;
    private  BigInteger creditCardNumber;
    private  String expiryDate;
    private  Integer cvc;
    private  String address;

    private  String postcode;

    private  String accountHolderName;


    public CreditCard(double amount, BigInteger creditCardNumber, String expiryDate, int cvc, String address, String postcode, String accountHolderName){

        this.amount =  amount;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.address = address;
        this.postcode = postcode;
        this.accountHolderName = accountHolderName;

    }

    public double getAmount(){
        return amount;
    }

    public BigInteger getCreditCardNumber(){
        return creditCardNumber;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    public Integer getCvc() {
        return cvc;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getPostcode() {
        return postcode;
    }
    
    public String getAddress() {
        return address;
    }

}
