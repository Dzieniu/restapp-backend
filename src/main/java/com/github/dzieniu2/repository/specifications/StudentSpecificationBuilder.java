package com.github.dzieniu2.repository.specifications;

import com.github.dzieniu2.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentSpecificationBuilder {

    private final List<SearchCriteria> criterias;

    public StudentSpecificationBuilder() {
        criterias = new ArrayList<>();
    }

    public StudentSpecificationBuilder with(String key, String operation, Object value) {
        criterias.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Student> buildSpecification(String searchCriteria) {

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(searchCriteria + ",");
        while (matcher.find()) {
            this.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return build();
    }

    public Specification<Student> build() {
        if (criterias.size() == 0) {
            return null;
        }

        List<Specification<Student>> specs = new ArrayList<Specification<Student>>();
        for (SearchCriteria param : criterias) {
            specs.add(new StudentSpecification(param));
        }

        Specification<Student> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
