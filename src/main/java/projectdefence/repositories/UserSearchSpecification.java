package projectdefence.repositories;

import org.springframework.data.jpa.domain.Specification;
import projectdefence.models.entities.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSearchSpecification implements Specification<User> {

    private final String username;

    public UserSearchSpecification(String username) {
        this.username = username;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();


        if (username != null) {
            p.getExpressions().add(
                    cb.and(cb.equal(root.get("username"), username))
            );
        }
        p.getExpressions();
        return p;
    }
}
