package com.github.dzieniu2.repository.specifications;

import com.github.dzieniu2.entity.Student;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class StudentSpecification implements Specification<Student> {

    private SearchCriteria criteria;

    public StudentSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        try {
            switch (criteria.getOperation()) {
                case ":":
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                case "%":
                    return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                case ">":
                    return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                case "<":
                    return builder.lessThanOrEqualTo(root. get(criteria.getKey()), criteria.getValue().toString());
            }
        } catch (IllegalArgumentException e) {
//            throw new NoSuchFieldException("There is no field: " + criteria.getKey());
        }
        return null;
    }
}
