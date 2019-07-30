package org.blackdread.lib.restfilter.demo;

import org.blackdread.lib.restfilter.criteria.CriteriaUtil;
import org.blackdread.lib.restfilter.demo.jooq.tables.records.ChildEntityRecord;
import org.blackdread.lib.restfilter.spring.sort.JooqSortUtil;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.Instant.ofEpochSecond;
import static java.time.LocalDate.ofEpochDay;
import static org.blackdread.lib.restfilter.demo.jooq.tables.ChildEntity.CHILD_ENTITY;

/**
 * <p>Created on 2019-06-12</p>
 *
 * @author Yoann CAPLAIN
 */
@SpringBootTest(classes = SpringTestApp.class)
@Transactional
public class JooqChildEntityQueryServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private JooqChildEntityQueryService jooqChildEntityQueryService;

    private static final Instant CREATE_TIME = Instant.EPOCH.plus(30 * 365, ChronoUnit.DAYS);

    private static final Instant CREATE_TIME_PARENT = Instant.EPOCH.plus(60 * 365, ChronoUnit.DAYS);

    private ChildEntity childEntity;

    private ParentEntity parentEntity;

    private ChildCriteria childCriteria;

    private final ArrayList<Long> childPersistedIds = new ArrayList<>();

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

        parentEntity = new ParentEntity();
        parentEntity.name = "theNameParent";
        parentEntity.createTime = CREATE_TIME_PARENT;
        parentEntity.total = BigDecimal.ONE;
        parentEntity.count = 8;
        parentEntity.localDate = LocalDate.of(2021, 6, 4);
        parentEntity.aShort = 14;
        parentEntity.active = true;
        parentEntity.uuid = UUID.randomUUID();
        parentEntity.duration = Duration.ofDays(9);

        childCriteria = new ChildCriteria();

        childPersistedIds.clear();
    }

    @Test
    public void testFilterCriteriaEmptyReturnsAll() {
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);

        final Condition condition = jooqChildEntityQueryService.createCondition(childCriteria);
        Assertions.assertEquals(DSL.trueCondition(), condition);

        long count = jooqChildEntityQueryService.count(childCriteria);
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));

        Assertions.assertEquals(2, count);
        Assertions.assertEquals(2, all.size());
        Assertions.assertEquals(1, page.getNumberOfElements());
        Assertions.assertEquals(2, page.getTotalPages());
    }

    @Test
    public void testFilterReturnsNoEntity() {
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);

        childCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        long count = jooqChildEntityQueryService.count(childCriteria);
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));

        Assertions.assertEquals(0, count);
        Assertions.assertEquals(0, all.size());
        Assertions.assertEquals(0, page.getTotalElements());
    }

    @Test
    public void testFilterFindOneEntity() {
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);

        childCriteria.id = CriteriaUtil.buildEqualsCriteria(childPersistedIds.get(0));

    }


    @Test
    public void testRangeFilter() {

    }


    @Test
    public void testInstantFilter() {
    }

    @Test
    public void testFilterOnJoinParentName() {
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        parentRepository.saveAndFlush(parentEntity);
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);

        childCriteria.parentName = CriteriaUtil.buildEqualsCriteria(parentEntity.name);

        long count = jooqChildEntityQueryService.count(childCriteria);
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));

        Assertions.assertEquals(1, count);
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void testFilterOnJoinParentNameWithMany() {
        childEntity.setParent(parentEntity);
        parentRepository.saveAndFlush(parentEntity);
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);

        childCriteria.parentName = CriteriaUtil.buildEqualsCriteria(parentEntity.name);

        long count = jooqChildEntityQueryService.count(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);

        Assertions.assertEquals(2, count);
        Assertions.assertEquals(2, all.size());
        Assertions.assertEquals(2, page.getTotalElements());
    }

    @Test
    public void testFilterOnJoinParentTotal() {
        childEntity.setParent(parentEntity);
        parentEntity.active = false;
        parentRepository.saveAndFlush(parentEntity);
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);
        em.detach(parentEntity);
        em.detach(childEntity);
        parentEntity.id = null;
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        parentEntity.active = true;
        parentRepository.saveAndFlush(parentEntity);
        childRepository.saveAndFlush(childEntity);
        childPersistedIds.add(childEntity.id);

        childCriteria.parentActive = CriteriaUtil.buildEqualsCriteria(parentEntity.active);

        long count = jooqChildEntityQueryService.count(childCriteria);
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));

        Assertions.assertEquals(1, count);
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(1, page.getTotalElements());
    }

    private void buildDataForSortTest() {
        childRepository.saveAndFlush(childEntity);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.count = 9;
        childEntity.active = true;
        childEntity.createTime = ofEpochSecond(2000);
        childEntity.localDate = ofEpochDay(600);
        childEntity.total = BigDecimal.valueOf(10001);
        childEntity.name = "aaaaaaaaaaaB";
        childRepository.saveAndFlush(childEntity);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.count = 15;
        childEntity.active = false;
        childEntity.createTime = ofEpochSecond(1000);
        childEntity.localDate = ofEpochDay(500);
        childEntity.total = BigDecimal.valueOf(10000);
        childEntity.name = "aaaaaaaaaaaC";
        childRepository.saveAndFlush(childEntity);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.count = 12;
        childEntity.active = true;
        childEntity.createTime = ofEpochSecond(3000);
        childEntity.localDate = ofEpochDay(200);
        childEntity.total = BigDecimal.valueOf(10004);
        childEntity.name = "aaaaaaaaaaaE";
        childRepository.saveAndFlush(childEntity);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.count = 1;
        childEntity.active = false;
        childEntity.createTime = ofEpochSecond(500);
        childEntity.localDate = ofEpochDay(900);
        childEntity.total = BigDecimal.valueOf(1000);
        childEntity.name = "aaaaaaaaaaa";
        childRepository.saveAndFlush(childEntity);
        em.detach(childEntity);
    }

    @Test
    public void jooqSortingSameWhenEmptySort() {
        buildDataForSortTest();

        final List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(null);
        final List<ChildEntityRecord> allSorted = jooqChildEntityQueryService.findAll(null, Sort.unsorted());
        final Page<ChildEntityRecord> allSorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50));

        final List<Long> list = all.stream().map(ChildEntityRecord::getId).collect(Collectors.toList());
        final List<Long> list1 = allSorted.stream().map(ChildEntityRecord::getId).collect(Collectors.toList());
        final List<Long> list2 = allSorted2.stream().map(ChildEntityRecord::getId).collect(Collectors.toList());

        Assertions.assertEquals(list.size(), allSorted.size());
        Assertions.assertEquals(list, list1);
        Assertions.assertEquals(list, list2);
    }

    @Test
    public void buildWithNullSort() {
        final Collection<? extends SortField<?>> sortFields = JooqSortUtil.buildOrderBy(null, CHILD_ENTITY.fields());
        final Collection<? extends SortField<?>> sortFields2 = JooqSortUtil.buildOrderBy(null, Arrays.asList(CHILD_ENTITY.fields()));

        Assertions.assertEquals(Collections.emptyList(), sortFields);
        Assertions.assertEquals(Collections.emptyList(), sortFields2);
    }

    @Test
    public void buildWithEmptyFields() {
        final Collection<? extends SortField<?>> sortFields = JooqSortUtil.buildOrderBy(null, (Field<?>) null);
        final Collection<? extends SortField<?>> sortFields1 = JooqSortUtil.buildOrderBy(null);
        final Collection<? extends SortField<?>> sortFields2 = JooqSortUtil.buildOrderBy(null, Collections.emptyList());

        Assertions.assertEquals(Collections.emptyList(), sortFields);
        Assertions.assertEquals(Collections.emptyList(), sortFields1);
        Assertions.assertEquals(Collections.emptyList(), sortFields2);
    }

    @Test
    public void buildWithNullFirst() {
        buildDataForSortTest();

        childEntity.id = null;
        childEntity.count = null;
        childRepository.saveAndFlush(childEntity);

        final Sort sort = Sort.by(Sort.Order.asc(CHILD_ENTITY.COUNT.getName()).nullsFirst());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Integer> list = sorted.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());
        final List<Integer> list2 = sorted2.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());

        final List<Integer> expected = Arrays.asList(null, 1, 5, 9, 12, 15);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void buildWithNullLast() {
        buildDataForSortTest();

        childEntity.id = null;
        childEntity.count = null;
        childRepository.saveAndFlush(childEntity);

        final Sort sort = Sort.by(Sort.Order.asc(CHILD_ENTITY.COUNT.getName()).nullsLast());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Integer> list = sorted.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());
        final List<Integer> list2 = sorted2.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());

        final List<Integer> expected = Arrays.asList(1, 5, 9, 12, 15, null);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void sortOnUnknownFieldIsIgnored() {
        buildDataForSortTest();

        final Sort sort = Sort.by("iDoNotExist", "meToo2");
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Integer> list = sorted.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());
        final List<Integer> list2 = sorted2.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());

        final List<Integer> expected = Arrays.asList(5, 9, 15, 12, 1);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByInt() {
        buildDataForSortTest();

        final Sort sort = Sort.by(CHILD_ENTITY.COUNT.getName());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Integer> list = sorted.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());
        final List<Integer> list2 = sorted2.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());

        final List<Integer> expected = Arrays.asList(1, 5, 9, 12, 15);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByIntDesc() {
        buildDataForSortTest();

        final Sort sort = Sort.by(Sort.Order.desc(CHILD_ENTITY.COUNT.getName()));
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Integer> list = sorted.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());
        final List<Integer> list2 = sorted2.stream().map(ChildEntityRecord::getCount).collect(Collectors.toList());

        final List<Integer> expected = Arrays.asList(1, 5, 9, 12, 15);
        Collections.reverse(expected);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByBool() {
        buildDataForSortTest();

        final Sort sort = Sort.by(CHILD_ENTITY.ACTIVE.getName());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Boolean> list = sorted.stream().map(ChildEntityRecord::getActive).collect(Collectors.toList());
        final List<Boolean> list2 = sorted2.stream().map(ChildEntityRecord::getActive).collect(Collectors.toList());

        final List<Boolean> expected = Arrays.asList(false, false, true, true, true);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.reverse(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByBoolDesc() {
        buildDataForSortTest();

        final Sort sort = Sort.by(Sort.Order.desc(CHILD_ENTITY.ACTIVE.getName()));
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Boolean> list = sorted.stream().map(ChildEntityRecord::getActive).collect(Collectors.toList());
        final List<Boolean> list2 = sorted2.stream().map(ChildEntityRecord::getActive).collect(Collectors.toList());

        final List<Boolean> expected = Arrays.asList(true, true, true, false, false);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.reverse(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByInstant() {
        buildDataForSortTest();

        final Sort sort = Sort.by(CHILD_ENTITY.CREATE_TIME.getName());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Instant> list = sorted.stream().map(ChildEntityRecord::getCreateTime).collect(Collectors.toList());
        final List<Instant> list2 = sorted2.stream().map(ChildEntityRecord::getCreateTime).collect(Collectors.toList());

        final List<Instant> expected = Arrays.asList(ofEpochSecond(500), ofEpochSecond(1000), ofEpochSecond(2000), ofEpochSecond(3000), CREATE_TIME);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByInstantDesc() {
        buildDataForSortTest();

        final Sort sort = Sort.by(Sort.Order.desc(CHILD_ENTITY.CREATE_TIME.getName()));
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<Instant> list = sorted.stream().map(ChildEntityRecord::getCreateTime).collect(Collectors.toList());
        final List<Instant> list2 = sorted2.stream().map(ChildEntityRecord::getCreateTime).collect(Collectors.toList());

        final List<Instant> expected = Arrays.asList(ofEpochSecond(500), ofEpochSecond(1000), ofEpochSecond(2000), ofEpochSecond(3000), CREATE_TIME);
        Collections.reverse(expected);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByLocalDate() {
        buildDataForSortTest();

        final Sort sort = Sort.by(CHILD_ENTITY.LOCAL_DATE.getName());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<LocalDate> list = sorted.stream().map(ChildEntityRecord::getLocalDate).collect(Collectors.toList());
        final List<LocalDate> list2 = sorted2.stream().map(ChildEntityRecord::getLocalDate).collect(Collectors.toList());

        final List<LocalDate> expected = Arrays.asList(ofEpochDay(200), ofEpochDay(500), ofEpochDay(600), ofEpochDay(900), LocalDate.of(2019, 1, 15));
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByLocalDateDesc() {
        buildDataForSortTest();

        final Sort sort = Sort.by(Sort.Order.desc(CHILD_ENTITY.LOCAL_DATE.getName()));
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<LocalDate> list = sorted.stream().map(ChildEntityRecord::getLocalDate).collect(Collectors.toList());
        final List<LocalDate> list2 = sorted2.stream().map(ChildEntityRecord::getLocalDate).collect(Collectors.toList());

        final List<LocalDate> expected = Arrays.asList(ofEpochDay(200), ofEpochDay(500), ofEpochDay(600), ofEpochDay(900), LocalDate.of(2019, 1, 15));
        Collections.reverse(expected);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByString() {
        buildDataForSortTest();

        final Sort sort = Sort.by(CHILD_ENTITY.NAME.getName());
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<String> list = sorted.stream().map(ChildEntityRecord::getName).collect(Collectors.toList());
        final List<String> list2 = sorted2.stream().map(ChildEntityRecord::getName).collect(Collectors.toList());

        final List<String> expected = new ArrayList<>(list);
        Collections.sort(expected);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortByStringDesc() {
        buildDataForSortTest();

        final Sort sort = Sort.by(Sort.Order.desc(CHILD_ENTITY.NAME.getName()));
        final List<ChildEntityRecord> sorted = jooqChildEntityQueryService.findAll(null, sort);
        final Page<ChildEntityRecord> sorted2 = jooqChildEntityQueryService.findAll(null, PageRequest.of(0, 50, sort));
        final List<String> list = sorted.stream().map(ChildEntityRecord::getName).collect(Collectors.toList());
        final List<String> list2 = sorted2.stream().map(ChildEntityRecord::getName).collect(Collectors.toList());

        final List<String> expected = new ArrayList<>(list);
        Collections.sort(expected);
        Collections.reverse(expected);
        Assertions.assertEquals(expected, list);
        Assertions.assertEquals(expected, list2);
        Collections.shuffle(expected);
        Assertions.assertNotEquals(expected, list);
    }

    @Test
    public void jooqSortingSortWithAlias() {

    }

    @Test
    public void jooqSortingSortWithNamedParam() {

    }

    @Test
    public void jooqSortingSortWithInlineParam() {

    }
}
