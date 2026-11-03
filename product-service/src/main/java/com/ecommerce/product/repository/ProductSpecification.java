package com.ecommerce.product.repository;

import com.ecommerce.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    // ✅ Composable Specifications (best practice)

    /* ===================== SEARCH ===================== */
    public static Specification<Product> search(String search) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(search)) {
                return cb.conjunction();
            }

            String pattern = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    /* ===================== DATE RANGE ===================== */
    public static Specification<Product> dateRange(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dateFrom != null) {
                OffsetDateTime start =
                        dateFrom.atStartOfDay()
                                .atOffset(ZoneOffset.UTC);

                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"), start
                        ));
            }

            if (dateTo != null) {
                OffsetDateTime end =
                        dateTo.plusDays(1)
                                .atStartOfDay()
                                .atOffset(ZoneOffset.UTC);

                predicates.add(
                        cb.lessThan(
                                root.get("createdAt"), end
                        ));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
