package com.ironhack.midtermproject.model.DTO;

import jakarta.validation.constraints.NotNull;

public class ThirdPartyUserDTO {

    @NotNull
    private String name;


    public ThirdPartyUserDTO(String name) {
        this.name = name;
    }

    public ThirdPartyUserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
