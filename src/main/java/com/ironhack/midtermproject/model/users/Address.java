package com.ironhack.midtermproject.model.users;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    private String streetAddress;

    @NotNull
    private String city;

    @NotNull
    private String state;



    //Constructor
    public Address(String streetAddress, String city, String state) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
    }

    public Address() {
    }

    //Getters and Setters
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



}
