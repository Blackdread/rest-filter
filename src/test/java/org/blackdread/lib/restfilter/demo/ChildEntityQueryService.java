package org.blackdread.lib.restfilter.demo;

import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.spring.filter.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * This class is just a compile - test.
 */
@Service
public class ChildEntityQueryService implements QueryService<ChildEntity> {

    static class ChildEntityCriteria extends BaseEntityQueryService.BaseEntityCriteria {
        LongFilter parentId;

        public LongFilter getParentId() {
            return parentId;
        }
    }

    public Specification<ChildEntity> createSpecification(ChildEntityCriteria criteria) {
        Specification<ChildEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
//                specification = specification.and(buildSpecification(criteria.getId(), BaseEntity_.id));
                specification = specification.and(buildSpecification(criteria.getId(), root -> root.get(BaseEntity_.id)));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BaseEntity_.name));
            }
            if (criteria.getCreateTime() != null) {
//                specification = specification.and(buildSpecification(criteria.getCreateTime(), BaseEntity_.createTime));
                specification = specification.and(buildSpecification(criteria.getCreateTime(), root -> root.get(BaseEntity_.createTime)));
            }
            if (criteria.getTotal() != null) {
//                specification = specification.and(buildSpecification(criteria.getTotal(), BaseEntity_.total));
                specification = specification.and(buildSpecification(criteria.getTotal(), root -> root.get(BaseEntity_.total)));
            }
            if (criteria.getActive() != null) {
//                specification = specification.and(buildSpecification(criteria.getActive(), BaseEntity_.active));
                specification = specification.and(buildSpecification(criteria.getActive(), root -> root.get(BaseEntity_.active)));
            }
            if (criteria.getParentId() != null) {
//                specification = specification.and(buildReferringEntitySpecification(criteria.getParentId(), ChildEntity_.parent, ParentEntity_.id));
                specification = specification.and(buildSpecification(criteria.getId(), root -> root.join(ChildEntity_.parent).get(BaseEntity_.id)));
            }
        }
        return specification;
    }

}
