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
