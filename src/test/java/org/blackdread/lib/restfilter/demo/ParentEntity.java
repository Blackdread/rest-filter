package org.blackdread.lib.restfilter.demo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ParentEntity extends BaseEntity {
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<ChildEntity> child1s = new HashSet<>();

    public Set<ChildEntity> getChild1s() {
        return child1s;
    }

    public void setChild1s(Set<ChildEntity> child1s) {
        this.child1s = child1s;
    }
}
