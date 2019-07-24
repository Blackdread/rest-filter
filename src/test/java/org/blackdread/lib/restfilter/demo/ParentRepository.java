package org.blackdread.lib.restfilter.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Created on 2019/07/24.</p>
 *
 * @author Yoann CAPLAIN
 */
@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Long>, JpaSpecificationExecutor<ParentEntity> {
}
