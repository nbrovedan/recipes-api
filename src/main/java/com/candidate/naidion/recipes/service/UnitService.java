package com.candidate.naidion.recipes.service;

import com.candidate.naidion.recipes.dto.CreateUpdateUnitDTO;
import com.candidate.naidion.recipes.dto.UnitDTO;
import com.candidate.naidion.recipes.enums.ErrorsEnum;
import com.candidate.naidion.recipes.exception.UnitException;
import com.candidate.naidion.recipes.mapper.UnitMapper;
import com.candidate.naidion.recipes.repository.UnitRepository;
import com.candidate.naidion.recipes.specification.UnitSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<UnitDTO> listAll(String name, String initials) {
        var units = unitRepository.findAll(UnitSpecification.build(name, initials));
        if(units.isEmpty()){
            throw new UnitException(HttpStatus.NOT_FOUND, ErrorsEnum.NO_UNITS_FOUND);
        }
        return CollectionUtils.emptyIfNull(units).parallelStream()
                .map(UnitMapper::toUnitDTO)
                .collect(Collectors.toList());
    }

    public UnitDTO findById(Integer id) {
        var unit = unitRepository.findById(id)
                .orElseThrow(() -> new UnitException(HttpStatus.NOT_FOUND, ErrorsEnum.UNIT_NOT_FOUND_BY_ID, id));
        return UnitMapper.toUnitDTO(unit);
    }

    public void create(CreateUpdateUnitDTO createUpdateUnitDTO){
        var totalInitials = unitRepository.countByInitials(createUpdateUnitDTO.getInitials());
        if(totalInitials > 0){
            throw new UnitException(HttpStatus.PRECONDITION_FAILED, ErrorsEnum.INITIALS_ALREADY_EXISTS, createUpdateUnitDTO.getInitials());
        }
        unitRepository.save(UnitMapper.toUnit(createUpdateUnitDTO));
    }

    public void update(Integer id, CreateUpdateUnitDTO createUpdateUnitDTO) {
        var unit = unitRepository.findById(id)
                .orElseThrow(() -> new UnitException(HttpStatus.NOT_FOUND, ErrorsEnum.UNIT_NOT_FOUND_BY_ID, id));
        unitRepository.save(unit.withInitials(createUpdateUnitDTO.getInitials())
                .withName(createUpdateUnitDTO.getName()));
    }
}
