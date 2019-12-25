/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
