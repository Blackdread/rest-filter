package org.blackdread.lib.restfilter.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Created on 2019/07/09.</p>
 *
 * @author Yoann CAPLAIN
 */
@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long>, JpaSpecificationExecutor<ChildEntity> {
}
