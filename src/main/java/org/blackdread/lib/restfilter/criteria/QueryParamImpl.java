package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * <p>Created on 2019/10/24.</p>
 *
 * @author Yoann CAPLAIN
 */
class QueryParamImpl implements QueryParam {

    private static final Logger log = LoggerFactory.getLogger(QueryParamImpl.class);

    @Override
    public String getCriteriaName() {
        return null;
    }

    @Override
    public boolean hasParamValue() {
        return false;
    }

    @Override
    public boolean hasMultipleParamValue() {
        return false;
    }

    @Nullable
    @Override
    public String getParamValue() {
        return null;
    }

    @Override
    public Optional<String> getParamValueOpt() {
        return Optional.empty();
    }

    @Override
    public List<String> getParamValues() {
        return null;
    }
}
