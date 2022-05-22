package com.ironhack.midtermproject.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Entity
@Table(name = "third_party_users")
public class ThirdPartyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer thirdPartyUserId;

    @Column(name = "hash_key")
    private String hashKey;

    @NotNull
    private String name;

    //Constructor
    public ThirdPartyUser(String hashKey, String name) {
        this.hashKey = generateHashKey().toString();
        this.name = name;
    }

    public ThirdPartyUser(String name) {
        this.name = name;
    }

    public ThirdPartyUser() {
    }


    //Getters and setters
    public Integer getThirdPartyUserId() {
        return thirdPartyUserId;
    }

    public void setThirdPartyUserId(Integer thirdPartyUserId) {
        this.thirdPartyUserId = thirdPartyUserId;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //method for generating a hashKey
    public static byte[] generateHashKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey.getEncoded();
    }
}
