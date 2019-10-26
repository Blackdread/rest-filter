package org.blackdread.lib.restfilter.criteria.parser;

import org.blackdread.lib.restfilter.criteria.annotation.CriteriaAlias;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * All data that can be extracted from method, annotations, etc
 */
@ThreadSafe
@Immutable
final class CriteriaMethodData {
    private final Method method;

    private final boolean isValue;
    private final boolean isFilter;
    private final boolean isIterable;

    private final String methodName;

    @Nullable
    private final CriteriaAlias criteriaAlias;
    @Nullable
    private String methodAliasName;

    @Nullable
    private final CriteriaInclude criteriaInclude;
    @Nullable
    private final Class<?> userDefinedType;

    static CriteriaMethodData of(final Method method) {

        return null;
    }

    private CriteriaMethodData() {
        // extraction hard-coded as should not change based on user options/etc
    }
}
