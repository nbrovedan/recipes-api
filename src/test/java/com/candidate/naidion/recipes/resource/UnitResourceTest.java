package com.candidate.naidion.recipes.resource;

import com.candidate.naidion.recipes.config.IntegrationTestConfig;
import com.candidate.naidion.recipes.entity.Unit;
import com.candidate.naidion.recipes.repository.UnitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnitResourceTest extends IntegrationTestConfig {

    private static final Integer QUANTITY_UNITS = 16;

    @Autowired
    private UnitRepository unitRepository;

    @Test
    void shouldListAllUnits() throws Exception {
        mockMvc.perform(get("/units")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFilterUnitsByNameAndReturn1() throws Exception {
        mockMvc.perform(get("/units?name=Decilitre")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void shouldFilterUnitsByInitialsAndReturn1() throws Exception {
        mockMvc.perform(get("/units?initials=dl")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void shouldFilterUnitsByNameAndInitialsAndReturnNone() throws Exception {
        mockMvc.perform(get("/units?name=Quart&initials=dl")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldCreateANewUnit() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", "Mock");
        body.put("initials", "mck");
        mockMvc.perform(post("/units")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(201));
        var units = unitRepository.findAll();
        System.out.println(units);
        Assertions.assertTrue(QUANTITY_UNITS.compareTo(units.size()) < 0);
    }

    @Test
    void shouldNotCreateANewUnitWithoutRequired() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", null);
        mockMvc.perform(post("/units")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    void shouldNotPermitCreateDuplicateInitial() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", "Mock");
        body.put("initials", "tsp");
        mockMvc.perform(post("/units")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(412));
    }

    @Test
    void shouldFindById() throws Exception {
        mockMvc.perform(get("/units/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldNotFindById() throws Exception {
        mockMvc.perform(get("/units/9999")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateAnUnit() throws Exception {
        var newName = "Mock";
        var body = new HashMap<String, Object>();
        body.put("name", newName);
        body.put("initials", "unt");
        mockMvc.perform(put("/units/1")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(202));
        var unit = unitRepository.findById(1).orElse(Unit.builder().build());
        Assertions.assertEquals(newName, unit.getName());
    }

    @Test
    void shouldNotUpdateAnUnit() throws Exception {
        var body = new HashMap<String, Object>();
        body.put("name", "Mock");
        body.put("initials", "unt");
        mockMvc.perform(put("/units/9999")
                        .content(new ObjectMapper().writeValueAsString(body))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}