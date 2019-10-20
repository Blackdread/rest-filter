/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
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

import org.blackdread.lib.restfilter.filter.*;
import org.blackdread.lib.restfilter.spring.filter.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BaseEntityQueryService implements QueryService<BaseEntity> {

    static class BaseEntityCriteria {
        LongFilter id;
        StringFilter name;
        InstantFilter createTime;
        BigDecimalFilter total;
        BooleanFilter active;
        IntegerFilter count;
        LocalDateFilter localDate;
        ShortFilter aShort;
        UUIDFilter uuid;
        DurationFilter duration;

        public LongFilter getId() {
            return id;
        }

        public StringFilter getName() {
            return name;
        }

        public InstantFilter getCreateTime() {
            return createTime;
        }

        public BigDecimalFilter getTotal() {
            return total;
        }

        public BooleanFilter getActive() {
            return active;
        }

        public IntegerFilter getCount() {
            return count;
        }

        public LocalDateFilter getLocalDate() {
            return localDate;
        }

        public ShortFilter getaShort() {
            return aShort;
        }

        public UUIDFilter getUuid() {
            return uuid;
        }

        public DurationFilter getDuration() {
            return duration;
        }
    }

    public Specification<BaseEntity> createSpecification(BaseEntityCriteria criteria) {
        Specification<BaseEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BaseEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BaseEntity_.name));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildSpecification(criteria.getCreateTime(), BaseEntity_.createTime));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildSpecification(criteria.getTotal(), BaseEntity_.total));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), BaseEntity_.active));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildSpecification(criteria.getCount(), BaseEntity_.count));
            }
            if (criteria.getLocalDate() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalDate(), BaseEntity_.localDate));
            }
            if (criteria.getaShort() != null) {
                specification = specification.and(buildSpecification(criteria.getaShort(), BaseEntity_.aShort));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildSpecification(criteria.getUuid(), BaseEntity_.uuid));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildSpecification(criteria.getDuration(), BaseEntity_.duration));
            }
        }
        return specification;
    }
}
