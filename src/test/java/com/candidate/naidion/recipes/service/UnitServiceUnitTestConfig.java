package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.config.UnitTestConfig;
import com.candidate.naidion.recipes.dto.CreateUpdateUnitDTO;
import com.candidate.naidion.recipes.entity.Unit;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.UnitException;
import com.candidate.naidion.recipes.repository.UnitRepository;
import com.candidate.naidion.recipes.specification.UnitSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("UnitServiceTest")
class UnitServiceUnitTestConfig extends UnitTestConfig {

    @MockBean
    private UnitRepository unitRepository;
    @Autowired
    private UnitService unitService;

    @Test
    void shouldListAllUnits() {
        var specificationMockedStatic = Mockito.mockStatic(UnitSpecification.class);
        specificationMockedStatic.when(() -> UnitSpecification.build(null, null)).thenReturn(Specification.where(null));
        var units = List.of(Mockito.mock(Unit.class), Mockito.mock(Unit.class));
        when(unitRepository.findAll(any(Specification.class))).thenReturn(units);
        var unitsDTO = unitService.listAll(null, null);
        specificationMockedStatic.close();
        Assertions.assertEquals(units.size(), unitsDTO.size());
    }

    @Test
    void shouldThrowsNoUnitException() {
        var specificationMockedStatic = Mockito.mockStatic(UnitSpecification.class);
        specificationMockedStatic.when(() -> UnitSpecification.build(anyString(), anyString())).thenReturn(Specification.where(null));
        when(unitRepository.findAll(any(Specification.class))).thenReturn(Collections.<Unit>emptyList());
        var exception = Assertions.assertThrows(UnitException.class, () -> unitService.listAll(anyString(), anyString()));
        specificationMockedStatic.close();
        Assertions.assertEquals(ErrorsEnum.NO_UNITS_FOUND, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldFindById() {
        var idMock = 1;
        var unitMock = Mockito.mock(Unit.class);
        when(unitMock.getId()).thenReturn(idMock);
        when(unitRepository.findById(anyInt())).thenReturn(Optional.of(unitMock));
        var unitsDTO = unitService.findById(anyInt());
        Assertions.assertEquals(idMock, unitsDTO.getId());
    }

    @Test
    void shouldThrowsNoUnitByIdExceptionOnFindById() {
        when(unitRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(UnitException.class, () -> unitService.findById(anyInt()));
        Assertions.assertEquals(ErrorsEnum.UNIT_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldCreateAUnit() {
        when(unitRepository.countByInitials(anyString())).thenReturn(0L);
        unitService.create(Mockito.mock(CreateUpdateUnitDTO.class));
        Mockito.verify(unitRepository, Mockito.times(1)).save(any(Unit.class));
    }

    @Test
    void shouldThrowsInitialsAlreadyExists() {
        when(unitRepository.countByInitials(anyString())).thenReturn(1L);
        var unitMock = Mockito.mock(CreateUpdateUnitDTO.class);
        when(unitMock.getInitials()).thenReturn("mock");
        var exception = Assertions.assertThrows(UnitException.class, () -> unitService.create(unitMock));
        Assertions.assertEquals(ErrorsEnum.INITIALS_ALREADY_EXISTS, exception.getError());
        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatus());
    }

    @Test
    void shouldUpdateUnit() {
        var idMock = 1;
        var createMock = Mockito.mock(CreateUpdateUnitDTO.class);
        when(createMock.getInitials()).thenReturn("mck");
        when(createMock.getName()).thenReturn("mock");
        var unitMock = Mockito.mock(Unit.class);
        when(unitMock.withInitials(anyString())).thenReturn(unitMock);
        when(unitMock.withName(anyString())).thenReturn(unitMock);
        when(unitRepository.findById(anyInt())).thenReturn(Optional.of(unitMock));
        unitService.update(idMock, createMock);
        Mockito.verify(unitRepository, Mockito.times(1)).save(any(Unit.class));
    }

    @Test
    void shouldThrowsNoUnitByIdExceptionOnUpdate() {
        var idMock = 1;
        when(unitRepository.findById(anyInt())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(UnitException.class, () -> unitService.update(idMock, mock(CreateUpdateUnitDTO.class)));
        Assertions.assertEquals(ErrorsEnum.UNIT_NOT_FOUND_BY_ID, exception.getError());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}