package org.ably.farm_management.criteria;

import org.ably.farm_management.domain.entity.Farm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FarmSpecificationBuilder {

    public Specification<Farm> build(FarmSearchCriteria criteria) {
        return Specification
                .where(hasName(criteria.getName()))
                .and(hasLocation(criteria.getLocation()))
                .and(hasArea(criteria.getArea()))
                .and(hasCreatedAt(criteria.getCreatedAt()));
    }

    private Specification<Farm> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    private Specification<Farm> hasLocation(String location) {
        return (root, query, cb) -> {
            if (location == null) return null;
            return cb.like(cb.lower(root.get("location")),
                    "%" + location.toLowerCase() + "%");
        };
    }

    private Specification<Farm> hasArea(Double area) {
        return (root, query, cb) -> {
            if (area == null) return null;
            return cb.equal(root.get("area"), area);
        };
    }

    private Specification<Farm> hasCreatedAt(LocalDate createdAt) {
        return (root, query, cb) -> {
            if (createdAt == null) return null;
            return cb.equal(root.get("createdAt"), createdAt);
        };
    }
}
