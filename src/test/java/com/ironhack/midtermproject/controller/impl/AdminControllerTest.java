package com.ironhack.midtermproject.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Address;
import com.ironhack.midtermproject.model.users.ThirdPartyUser;
import com.ironhack.midtermproject.repository.AccountHolderRepository;
import com.ironhack.midtermproject.repository.CheckingRepository;
import com.ironhack.midtermproject.repository.ThirdPartyUserRepository;
import com.ironhack.midtermproject.service.impl.ThirdPartyUserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.With;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private ThirdPartyUserRepository thirdPartyUserRepository;




    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Liepu", "Klaipeda", "125");
        LocalDate date = LocalDate.of(1993, 5, 12);
        AccountHolder accountHolder = new AccountHolder("Edita", "1234", "Edita", date, address);
        accountHolderRepository.save(accountHolder);
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "Admin", password = "1234", roles = "ADMIN")
    public void saveThirdPartyUser_valid_successful() throws Exception {
        ThirdPartyUserDTO thirdPartyUserDTO = new ThirdPartyUserDTO("James");
        String body = objectMapper.writeValueAsString(thirdPartyUserDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admins/savethirdpartyuser")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("James"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "1234", roles = "ADMIN")
    public void saveCreditCard_valid_successful() throws Exception {
        Money balance = new Money(new BigDecimal(1500), Currency.getInstance("USD"));
        Money creditLimit = new Money(new BigDecimal(10000), Currency.getInstance("USD"));
        CreditCardDTO creditCardDTO = new CreditCardDTO(balance, "Laura", "Edita", 1,  creditLimit, new BigDecimal(0.15), LocalDate.now());
        String body = objectMapper.writeValueAsString(creditCardDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admins/savecreditcard")
                .contentType(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();


        assertTrue(mvcResult.getResponse().getContentAsString().contains("Laura"));

    }


    @Test
    @WithMockUser(username = "Admin", password = "1234", roles = "ADMIN")
    public void saveAccountChecking_AccHolderOlderThan24_Successful() throws Exception {
        Address address = new Address("Liepu", "Klaipeda", "125");
        LocalDate date = LocalDate.of(1993, 5, 12);
        AccountHolder accountHolder = new AccountHolder("Edita", "1234", "Edita", date, address);
        accountHolderRepository.save(accountHolder);

        CheckingDTO checkingDTO = new CheckingDTO(new Money(new BigDecimal(2000)), "Edita", "Laura", 1);
        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/admins/saveaccount")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
//
//        BigDecimal createdCheckingBalance = checkingRepository.findById(1).get().getBalance().getAmount();

//        assertTrue(createdCheckingBalance.compareTo(new BigDecimal(2000)) == 0);



    }


    @Test
    @WithMockUser(username = "Admin", password = "1234", roles = "ADMIN")
    public void deleteThirdPartyUser_valid_successful() throws Exception {
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser("Jess");
        thirdPartyUserRepository.save(thirdPartyUser);
        Integer thirdPartyUserId = 1;

        MvcResult mvcResult = mockMvc.perform(delete("/api/admins/deletethirdparty/1")
        ).andExpect(status().isOk()).andReturn();

        assertFalse(thirdPartyUserRepository.findById(1).isPresent());
    }


}