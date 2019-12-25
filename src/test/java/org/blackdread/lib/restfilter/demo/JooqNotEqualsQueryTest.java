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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

/**
 * <p>Created on 2019-10-06</p>
 *
 * @author Yoann CAPLAIN
 */
@SpringBootTest(classes = SpringTestApp.class)
@Transactional
public class JooqNotEqualsQueryTest {

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
    public void notEqualsReturnsAllExceptOneGiven() {

    }

    @Test
    public void notEqualsCanBeUsedWithSpecified() {

    }

    @Test
    public void notEqualsCanBeUsedWithRange() {

    }
}
