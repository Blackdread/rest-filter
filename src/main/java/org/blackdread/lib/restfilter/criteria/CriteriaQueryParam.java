package org.blackdread.lib.restfilter.criteria;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CriteriaQueryParam {


    default Object buildQueryParams(Object criteria) {
        throw new IllegalStateException("todo");
    }

//    default Object buildQueryParams(Object criteria) {
//        throw new IllegalStateException("todo");
//    }

    default Object buildQueryParams(Criteria criteria) {
        throw new IllegalStateException("todo");
    }

//    default Object buildQueryParams(Criteria criteria) {
//        throw new IllegalStateException("todo");
//    }
}

