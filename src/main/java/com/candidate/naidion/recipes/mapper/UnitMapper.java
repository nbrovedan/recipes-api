package com.candidate.naidion.recipes.mapper;

import com.candidate.naidion.recipes.dto.CreateUpdateUnitDTO;
import com.candidate.naidion.recipes.dto.UnitDTO;
import com.candidate.naidion.recipes.entity.Unit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitMapper {

    public static UnitDTO toUnitDTO(Unit unit){
        return UnitDTO.builder()
                .id(unit.getId())
                .initials(unit.getInitials())
                .name(unit.getName())
                .build();
    }

    public static Unit toUnit(CreateUpdateUnitDTO createUpdateUnitDTO){
        return Unit.builder()
                .initials(createUpdateUnitDTO.getInitials())
                .name(createUpdateUnitDTO.getName())
                .build();
    }

    public static Unit toUnit(UnitDTO unitDTO){
        return Unit.builder()
                .id(unitDTO.getId())
                .initials(unitDTO.getInitials())
                .name(unitDTO.getName())
                .build();
    }
}
