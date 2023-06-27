package com.acme.dataobjects;

public class CreditCard {
    private  Integer amount;
    private  int creditCardNumber;
    private  String expiryDate;
    private  Integer cvc;
    private  String address;

    private  String postcode;

    private  String accountHolderName;


    public CreditCard(int amount, int creditCardNumber, String expiryDate, int cvc, String address, String postcode, String accountHolderName){

        this.amount =  amount;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.address = address;
        this.postcode = postcode;
        this.accountHolderName = accountHolderName;

    }

    public int getAmount(){
        return amount;
    }

    public int getCreditCardNumber(){
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
