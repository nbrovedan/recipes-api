package com.candidate.naidion.recipes.specification;

import com.candidate.naidion.recipes.entity.Unit;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UnitSpecification {

    public static Specification<Unit> build(String name, String initials){
         return (root, query, cb) -> {
            var filter = emptyWhere();
            if(name != null) filter = filter.and(unitsLikeName(name));
            if(initials != null) filter = filter.and(unitsLikeInitials(initials));
            return filter.toPredicate(root, query, cb);
        };
    }

    private static Specification<Unit> emptyWhere(){
        return (root, query, cb) -> cb.equal(cb.literal(1), 1);
    }

    private static Specification<Unit> unitsLikeName(@NonNull String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    private static Specification<Unit> unitsLikeInitials(@NonNull String initials) {
        return (root, query, cb) -> cb.like(root.get("initials"), "%" + initials + "%");
    }
}
