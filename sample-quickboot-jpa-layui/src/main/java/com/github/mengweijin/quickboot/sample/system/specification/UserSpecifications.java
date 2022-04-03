package com.github.mengweijin.quickboot.sample.system.specification;

import com.github.mengweijin.quickboot.sample.system.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author mengweijin
 * @date 2022/4/2
 */
public class UserSpecifications {

    public static Specification<User> concatWith(String username, String with) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Expression<String> concatCol = concatWith(criteriaBuilder, root.get("name"), with);
                Expression concatVal = concatWith(criteriaBuilder, criteriaBuilder.literal(username), with);
                return criteriaBuilder.equal(concatCol, concatVal);
            }
        };
    }

    public static Expression<String> concatWith(CriteriaBuilder criteriaBuilder, Expression expression, String with) {
        return criteriaBuilder.function("CONCAT", String.class, expression, criteriaBuilder.literal(with));
    }

    /**
     * 左边填充
     *
     * @param criteriaBuilder cb
     * @param expression      来源表达式
     * @param length          达到长度目标
     * @param with            使用什么字符填充
     * @return 「左边填充」的表达式
     */
    public static Expression<String> leftPaddingWith(CriteriaBuilder criteriaBuilder,
                                                     Expression expression,
                                                     int length,
                                                     char with) {
        return criteriaBuilder.function("LPAD", String.class, expression,
                criteriaBuilder.literal(length),
                criteriaBuilder.literal(with));
    }

}
