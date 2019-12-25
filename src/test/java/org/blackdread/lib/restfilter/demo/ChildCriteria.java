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

import org.blackdread.lib.restfilter.criteria.Criteria;
import org.blackdread.lib.restfilter.filter.*;

import java.util.Objects;

/**
 * <p>Created on 2019/07/08.</p>
 *
 * @author Yoann CAPLAIN
 */
public class ChildCriteria implements Criteria {

    LongFilter id;

    StringFilter name;

    InstantFilter createTime;

    BigDecimalFilter total;

    IntegerFilter count;

    LocalDateFilter localDate;

    ShortFilter aShort;

    BooleanFilter active;

    UUIDFilter uuid;

    DurationFilter duration;

    LongFilter parentId;

    /*
     * Below with filtering on fields of other table
     */

    StringFilter parentName;

    BigDecimalFilter parentTotal;

    BooleanFilter parentActive;

    public ChildCriteria() {
    }

    public ChildCriteria(ChildCriteria copy) {
        id = copy.id.copy();
        name = copy.name.copy();
        createTime = copy.createTime.copy();
        total = copy.total.copy();
        count = copy.count.copy();
        localDate = copy.localDate.copy();
        aShort = copy.aShort.copy();
        active = copy.active.copy();
        uuid = copy.uuid.copy();
        duration = copy.duration.copy();
        parentId = copy.parentId.copy();
        parentName = copy.parentName.copy();
        parentTotal = copy.parentTotal.copy();
        parentActive = copy.parentActive.copy();
    }

    @Override
    public ChildCriteria copy() {
        return new ChildCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public BigDecimalFilter getTotal() {
        return total;
    }

    public void setTotal(BigDecimalFilter total) {
        this.total = total;
    }

    public IntegerFilter getCount() {
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
    }

    public LocalDateFilter getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateFilter localDate) {
        this.localDate = localDate;
    }

    public ShortFilter getaShort() {
        return aShort;
    }

    public void setaShort(ShortFilter aShort) {
        this.aShort = aShort;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public UUIDFilter getUuid() {
        return uuid;
    }

    public void setUuid(UUIDFilter uuid) {
        this.uuid = uuid;
    }

    public DurationFilter getDuration() {
        return duration;
    }

    public void setDuration(DurationFilter duration) {
        this.duration = duration;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public StringFilter getParentName() {
        return parentName;
    }

    public void setParentName(StringFilter parentName) {
        this.parentName = parentName;
    }

    public BigDecimalFilter getParentTotal() {
        return parentTotal;
    }

    public void setParentTotal(BigDecimalFilter parentTotal) {
        this.parentTotal = parentTotal;
    }

    public BooleanFilter getParentActive() {
        return parentActive;
    }

    public void setParentActive(BooleanFilter parentActive) {
        this.parentActive = parentActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildCriteria that = (ChildCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(total, that.total) &&
            Objects.equals(count, that.count) &&
            Objects.equals(localDate, that.localDate) &&
            Objects.equals(aShort, that.aShort) &&
            Objects.equals(active, that.active) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(parentName, that.parentName) &&
            Objects.equals(parentTotal, that.parentTotal) &&
            Objects.equals(parentActive, that.parentActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createTime, total, count, localDate, aShort, active, uuid, duration, parentId, parentName, parentTotal, parentActive);
    }
}
