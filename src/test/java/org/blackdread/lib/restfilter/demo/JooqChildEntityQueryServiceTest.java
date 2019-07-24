package org.blackdread.lib.restfilter.demo;

import org.blackdread.lib.restfilter.criteria.CriteriaUtil;
import org.blackdread.lib.restfilter.demo.jooq.tables.records.ChildEntityRecord;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
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
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        em.persist(childEntity);
        em.flush();
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
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        em.persist(parentEntity);
        em.persist(childEntity);
        em.flush();
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
        em.persist(parentEntity);
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(childEntity);
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        em.persist(childEntity);
        em.flush();
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
        em.persist(parentEntity);
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);
        em.detach(parentEntity);
        em.detach(childEntity);
        parentEntity.id = null;
        childEntity.id = null;
        childEntity.setParent(parentEntity);
        parentEntity.active = true;
        em.persist(parentEntity);
        em.persist(childEntity);
        em.flush();
        childPersistedIds.add(childEntity.id);

        childCriteria.parentActive = CriteriaUtil.buildEqualsCriteria(parentEntity.active);

        long count = jooqChildEntityQueryService.count(childCriteria);
        List<ChildEntityRecord> all = jooqChildEntityQueryService.findAll(childCriteria);
        Page<ChildEntityRecord> page = jooqChildEntityQueryService.findAll(childCriteria, PageRequest.of(0, 1));

        Assertions.assertEquals(1, count);
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(1, page.getTotalElements());
    }

}
