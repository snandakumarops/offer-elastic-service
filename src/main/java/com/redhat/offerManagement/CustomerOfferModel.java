package com.redhat.offerManagement;

import java.io.Serializable;

public class CustomerOfferModel implements Serializable {

    private String custId;
    private Double custAge;
    private String custClass;
    private Double custIncome;
    private String qualifiedPurchases;
    private Integer lastOfferResponse;
    private String customerSegmentation;
    private String activeOffers;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Double getCustAge() {
        return custAge;
    }

    public void setCustAge(Double custAge) {
        this.custAge = custAge;
    }

    public String getCustClass() {
        return custClass;
    }

    public void setCustClass(String custClass) {
        this.custClass = custClass;
    }

    public Double getCustIncome() {
        return custIncome;
    }

    public void setCustIncome(Double custIncome) {
        this.custIncome = custIncome;
    }

    public String getQualifiedPurchases() {
        return qualifiedPurchases;
    }

    public void setQualifiedPurchases(String qualifiedPurchases) {
        this.qualifiedPurchases = qualifiedPurchases;
    }

    public Integer getLastOfferResponse() {
        return lastOfferResponse;
    }

    public void setLastOfferResponse(Integer lastOfferResponse) {
        this.lastOfferResponse = lastOfferResponse;
    }

    public String getCustomerSegmentation() {
        return customerSegmentation;
    }

    public void setCustomerSegmentation(String customerSegmentation) {
        this.customerSegmentation = customerSegmentation;
    }

    public String getActiveOffers() {
        return activeOffers;
    }

    public void setActiveOffers(String activeOffers) {
        this.activeOffers = activeOffers;
    }

    @Override
    public String toString() {
        return "CustomerOfferModel{" +
                "custId='" + custId + '\'' +
                ", custAge=" + custAge +
                ", custClass='" + custClass + '\'' +
                ", custIncome=" + custIncome +
                ", qualifiedPurchases='" + qualifiedPurchases + '\'' +
                ", lastOfferResponse=" + lastOfferResponse +
                ", customerSegmentation='" + customerSegmentation + '\'' +
                ", activeOffers='" + activeOffers + '\'' +
                '}';
    }
}
