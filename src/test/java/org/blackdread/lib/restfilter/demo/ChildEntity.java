package org.blackdread.lib.restfilter.demo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ChildEntity extends BaseEntity {

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private ParentEntity parent;

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }
}
