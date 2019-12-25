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

import org.blackdread.lib.restfilter.criteria.CriteriaUtil;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>Created on 2019-06-12</p>
 *
 * @author Yoann CAPLAIN
 */
@SpringBootTest(classes = SpringTestApp.class)
@Transactional
public class BaseEntityQueryServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ChildEntityQueryService childEntityQueryService;

    private static final Instant CREATE_TIME = Instant.EPOCH.plus(30 * 365, ChronoUnit.DAYS);

    private ChildEntity childEntity;

    private ChildEntityQueryService.ChildEntityCriteria childCriteria;

    private LongFilter longFilter;
    private StringFilter stringFilter;

    @BeforeEach
    public void setup() {
        childEntity = new ChildEntity();
        childEntity.name = "theName";
        childEntity.createTime = CREATE_TIME;
        childEntity.total = BigDecimal.TEN;
        childEntity.count = 5;
        childEntity.localDate = LocalDate.of(2019, 1, 15);
        childEntity.aShort = 2;
        childEntity.active = true;
        childEntity.uuid = UUID.randomUUID();
        childEntity.duration = Duration.ofDays(4);
        childEntity.setParent(null);

        childCriteria = new ChildEntityQueryService.ChildEntityCriteria();

        longFilter = new LongFilter();
        stringFilter = new StringFilter();
    }

    private static void fillAll(final LongFilter filter) {
        filter.setEquals(1L);
        filter.setIn(Arrays.asList(1L, 2L));
        filter.setSpecified(true);
        filter.setGreaterThan(1L);
        filter.setGreaterThanOrEqual(1L);
        filter.setLessThan(1L);
        filter.setLessThanOrEqual(1L);
    }

    private static void fillAll(final StringFilter filter) {
        filter.setEquals("any");
        filter.setIn(Arrays.asList("any", "any2"));
        filter.setSpecified(true);
        filter.setContains("any");
    }

    @Test
    public void testFilterCriteriaEmptyReturnsAll() {
        final ArrayList<Long> ids = new ArrayList<>();
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);

        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);

        final List<ChildEntity> all = childRepository.findAll(spec);

        Assertions.assertEquals(2, all.size());
        Assertions.assertEquals(ids, all.stream().map(e -> e.id).collect(Collectors.toList()));
    }

    @Test
    public void testFilterReturnsNoEntity() {
        final ArrayList<Long> ids = new ArrayList<>();
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);

        childCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);

        final List<ChildEntity> all = childRepository.findAll(spec);

        Assertions.assertEquals(Collections.emptyList(), all);
        final List<ChildEntity> all2 = childRepository.findAll();
        Assertions.assertEquals(2, all2.size());
    }

    @Test
    public void testFilterFindOneEntity() {
        final ArrayList<Long> ids = new ArrayList<>();
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
        ids.add(childEntity.id);

        childCriteria.id = CriteriaUtil.buildEqualsCriteria(ids.get(0));

        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);

        final List<ChildEntity> all = childRepository.findAll(spec);

        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(em.find(ChildEntity.class, ids.get(0)), all.get(0));
    }

    @Test
    public void testFilterEquals() {
        childCriteria.id = longFilter;
        fillAll(longFilter);
        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);
        final List<ChildEntity> all = childRepository.findAll(spec);
        Assertions.assertEquals(0, all.size());
    }


    @Test
    public void testFilterIn() {
        childCriteria.id = longFilter;
        fillAll(longFilter);
        longFilter.setEquals(null);
        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);
        final List<ChildEntity> all = childRepository.findAll(spec);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    public void testFilterSpecified() {
        childCriteria.id = longFilter;
        fillAll(longFilter);
        longFilter.setEquals(null);
        longFilter.setIn(null);
        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);
        final List<ChildEntity> all = childRepository.findAll(spec);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    public void testFilterOthers() {
        childCriteria.id = longFilter;
        fillAll(longFilter);
        longFilter.setEquals(null);
        longFilter.setIn(null);
        final Specification<ChildEntity> spec = childEntityQueryService.createSpecification(childCriteria);
        final List<ChildEntity> all = childRepository.findAll(spec);
        Assertions.assertEquals(0, all.size());
    }


    @Test
    public void testRangeFilter() {

    }


    @Test
    public void testInstantFilter() {

    }

}
