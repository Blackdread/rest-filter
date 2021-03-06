/*
 * This file is generated by jOOQ.
 */
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
package org.blackdread.lib.restfilter.demo.jooq.tables;


import org.blackdread.lib.restfilter.demo.converter.InstantConverter;
import org.blackdread.lib.restfilter.demo.jooq.Indexes;
import org.blackdread.lib.restfilter.demo.jooq.Keys;
import org.blackdread.lib.restfilter.demo.jooq.Public;
import org.blackdread.lib.restfilter.demo.jooq.tables.records.ParentEntityRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ParentEntity extends TableImpl<ParentEntityRecord> {

    private static final long serialVersionUID = -1087077059;

    /**
     * The reference instance of <code>PUBLIC.PARENT_ENTITY</code>
     */
    public static final ParentEntity PARENT_ENTITY = new ParentEntity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ParentEntityRecord> getRecordType() {
        return ParentEntityRecord.class;
    }

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.ID</code>.
     */
    public final TableField<ParentEntityRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.NAME</code>.
     */
    public final TableField<ParentEntityRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.CREATE_TIME</code>.
     */
    public final TableField<ParentEntityRecord, Instant> CREATE_TIME = createField("CREATE_TIME", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.precision(6).nullable(false), this, "", new InstantConverter());

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.TOTAL</code>.
     */
    public final TableField<ParentEntityRecord, BigDecimal> TOTAL = createField("TOTAL", org.jooq.impl.SQLDataType.DECIMAL(20, 5).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.COUNT</code>.
     */
    public final TableField<ParentEntityRecord, Integer> COUNT = createField("COUNT", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.LOCAL_DATE</code>.
     */
    public final TableField<ParentEntityRecord, LocalDate> LOCAL_DATE = createField("LOCAL_DATE", org.jooq.impl.SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.A_SHORT</code>.
     */
    public final TableField<ParentEntityRecord, Short> A_SHORT = createField("A_SHORT", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.ACTIVE</code>.
     */
    public final TableField<ParentEntityRecord, Boolean> ACTIVE = createField("ACTIVE", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.UUID</code>.
     */
    public final TableField<ParentEntityRecord, UUID> UUID = createField("UUID", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.PARENT_ENTITY.DURATION</code>.
     */
    public final TableField<ParentEntityRecord, String> DURATION = createField("DURATION", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.PARENT_ENTITY</code> table reference
     */
    public ParentEntity() {
        this(DSL.name("PARENT_ENTITY"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.PARENT_ENTITY</code> table reference
     */
    public ParentEntity(String alias) {
        this(DSL.name(alias), PARENT_ENTITY);
    }

    /**
     * Create an aliased <code>PUBLIC.PARENT_ENTITY</code> table reference
     */
    public ParentEntity(Name alias) {
        this(alias, PARENT_ENTITY);
    }

    private ParentEntity(Name alias, Table<ParentEntityRecord> aliased) {
        this(alias, aliased, null);
    }

    private ParentEntity(Name alias, Table<ParentEntityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ParentEntity(Table<O> child, ForeignKey<O, ParentEntityRecord> key) {
        super(child, key, PARENT_ENTITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ParentEntityRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ParentEntityRecord>> getKeys() {
        return Arrays.<UniqueKey<ParentEntityRecord>>asList(Keys.CONSTRAINT_6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParentEntity as(String alias) {
        return new ParentEntity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParentEntity as(Name alias) {
        return new ParentEntity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ParentEntity rename(String name) {
        return new ParentEntity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ParentEntity rename(Name name) {
        return new ParentEntity(name, null);
    }
}
