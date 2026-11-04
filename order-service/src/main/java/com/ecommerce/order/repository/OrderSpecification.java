package com.ecommerce.order.repository;

public class OrderSpecification {
<<<<<<< Updated upstream
=======

    // ✅ Composable Specifications (best practice)

    /* ===================== SEARCH ===================== */
    public static Specification<Order> search(String search) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(search)) {
                return cb.conjunction();
            }

            String pattern = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }

    /* ===================== DATE RANGE ===================== */
    public static Specification<Order> dateBetween(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

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
>>>>>>> Stashed changes
}
