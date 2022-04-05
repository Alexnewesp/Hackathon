package com.example.mercadillosmadridmapa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("postal-code")
    private String postalCode;

    @JsonProperty("street-address")
    private String streetAddress;

    public Address(String postalCode, String streetAddress) {
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
    }

    public Address() {
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress= streetAddress;
    }
}
