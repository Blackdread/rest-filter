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

import org.blackdread.lib.restfilter.demo.jooq.tables.records.ChildEntityRecord;
import org.blackdread.lib.restfilter.spring.sort.JooqSortUtil;
import org.blackdread.lib.restfilter.spring.filter.JooqQueryServiceImpl;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.blackdread.lib.restfilter.demo.jooq.Tables.PARENT_ENTITY;
import static org.blackdread.lib.restfilter.demo.jooq.tables.ChildEntity.CHILD_ENTITY;

@Service
@Transactional(readOnly = true)
public class JooqChildEntityQueryService {

    @Autowired
    private final DSLContext create;

    /**
     * No really need this service, could use directly the util class with static methods. In real context, it might be a single Service that is injected
     * Just to demonstrate the use with composition instead of inheritance
     */
    private final JooqQueryServiceImpl jooqQueryService = new JooqQueryServiceImpl();

    public JooqChildEntityQueryService(DSLContext create) {
        this.create = create;
    }

    public long count(ChildCriteria criteria) {
        Condition condition = createCondition(criteria);
        // could optionally join parent based on criteria but easier this way
        return create.selectCount()
            .from(CHILD_ENTITY)
            .leftJoin(PARENT_ENTITY).on(CHILD_ENTITY.PARENT_ID.eq(PARENT_ENTITY.ID))
            .where(condition)
            .fetchOne()
            .value1();
    }


    /**
     * We return the record directly but you could have your services to return a DTO, using jooq mapper or MapStruct
     */
    public List<ChildEntityRecord> findAll(ChildCriteria criteria) {
        Condition condition = createCondition(criteria);
        // could optionally join parent based on criteria but easier this way
        return create.select(CHILD_ENTITY.fields())
            .from(CHILD_ENTITY)
            .leftJoin(PARENT_ENTITY).on(CHILD_ENTITY.PARENT_ID.eq(PARENT_ENTITY.ID))
            .where(condition)
            .fetchInto(CHILD_ENTITY);
    }

    /**
     * We return the record directly but you could have your services to return a DTO, using jooq mapper or MapStruct
     */
    public List<ChildEntityRecord> findAll(ChildCriteria criteria, Sort sort) {
        Condition condition = createCondition(criteria);
        // could optionally join parent based on criteria but easier this way
        return create.select(CHILD_ENTITY.fields())
            .from(CHILD_ENTITY)
            .leftJoin(PARENT_ENTITY).on(CHILD_ENTITY.PARENT_ID.eq(PARENT_ENTITY.ID))
            .where(condition)
            .orderBy(JooqSortUtil.buildOrderBy(sort, CHILD_ENTITY.fields()))
            .fetchInto(CHILD_ENTITY);
    }

    /**
     * We return the record directly but you could have your services to return a DTO, using jooq mapper or MapStruct
     */
    public Page<ChildEntityRecord> findAll(ChildCriteria criteria, Pageable pageable) {
        Condition condition = createCondition(criteria);
        // could optionally join parent based on criteria but easier this way
        return new PageImpl<>(create.select(CHILD_ENTITY.fields())
            .from(CHILD_ENTITY)
            .leftJoin(PARENT_ENTITY).on(CHILD_ENTITY.PARENT_ID.eq(PARENT_ENTITY.ID))
            .where(condition)
            .orderBy(JooqSortUtil.buildOrderBy(pageable.getSort(), CHILD_ENTITY.fields()))
            .limit(pageable.getPageSize())
            .offset((int) pageable.getOffset())
            .fetchInto(CHILD_ENTITY), pageable, count(criteria));
    }

    public Condition createCondition(ChildCriteria criteria) {
        Condition condition = DSL.trueCondition();
        if (criteria != null) {
            if (criteria.getId() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getId(), CHILD_ENTITY.ID));
            }
            if (criteria.getName() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getName(), CHILD_ENTITY.NAME));
            }
            if (criteria.getCreateTime() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getCreateTime(), CHILD_ENTITY.CREATE_TIME));
            }
            if (criteria.getTotal() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getTotal(), CHILD_ENTITY.TOTAL));
            }
            if (criteria.getCount() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getCount(), CHILD_ENTITY.COUNT));
            }
            if (criteria.getLocalDate() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getLocalDate(), CHILD_ENTITY.LOCAL_DATE));
            }
            if (criteria.getaShort() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getaShort(), CHILD_ENTITY.A_SHORT));
            }
            if (criteria.getActive() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getActive(), CHILD_ENTITY.ACTIVE));
            }
            if (criteria.getUuid() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getUuid(), CHILD_ENTITY.UUID));
            }
            if (criteria.getDuration() != null) {
//                condition = condition.and(jooqQueryService.buildCondition(criteria.getDuration(), CHILD_ENTITY.DURATION));
            }
            if (criteria.getParentId() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getParentId(), CHILD_ENTITY.PARENT_ID));
            }
            if (criteria.getParentName() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getParentName(), PARENT_ENTITY.NAME));
            }
            if (criteria.getParentTotal() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getParentTotal(), PARENT_ENTITY.TOTAL));
            }
            if (criteria.getParentActive() != null) {
                condition = condition.and(jooqQueryService.buildCondition(criteria.getParentActive(), PARENT_ENTITY.ACTIVE));
            }
        }
        return condition;
    }

}
