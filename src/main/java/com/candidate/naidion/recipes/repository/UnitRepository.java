package com.candidate.naidion.recipes.repository;

import com.candidate.naidion.recipes.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UnitRepository extends JpaRepository<Unit, Integer>, JpaSpecificationExecutor<Unit> {

    long countByInitials(String initials);
}
