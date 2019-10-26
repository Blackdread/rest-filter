package org.blackdread.lib.restfilter.criteria.parser;

import org.blackdread.lib.restfilter.criteria.annotation.CriteriaAlias;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * All data that can be extracted from method, annotations, etc
 */
@ThreadSafe
@Immutable
public final class CriteriaMethodData {
    private final Method method;
    private final Class<?> methodReturnType;

    private final boolean isValue;
    private final boolean isFilter;
    private final boolean isIterable;
    private final boolean isArray;

    private final String methodName;
    private final String formattedMethodName;

    @Nullable
    private final CriteriaAlias criteriaAlias;
    @Nullable
    private String methodAliasName;

    @Nullable
    private final CriteriaInclude criteriaInclude;
    @Nullable
    private final Class<?> userDefinedType;

    static CriteriaMethodData of(final Method method) {
        return new CriteriaMethodData(Objects.requireNonNull(method));
    }

    private CriteriaMethodData(final Method method) {
        // extraction hard-coded as should not change based on user options/etc
        this.method = method;
        this.methodReturnType = method.getReturnType();

        this.isIterable = CriteriaFieldParserUtil.isIterableType(methodReturnType);
        this.isFilter = CriteriaFieldParserUtil.isFilterType(methodReturnType);
        this.isArray = methodReturnType.isArray();
        // Default is value if none above
        this.isValue = !isIterable && !isFilter && !isArray;

        this.methodName = method.getName();
        this.formattedMethodName = CriteriaFieldParserUtil.formatMethodName(method.getName());

        this.criteriaAlias = method.getAnnotation(CriteriaAlias.class);
        if (criteriaAlias != null) {
            this.methodAliasName = criteriaAlias.name();
        } else {
            methodAliasName = null;
        }

        this.criteriaInclude = method.getAnnotation(CriteriaInclude.class);
        if (criteriaInclude != null) {
            this.userDefinedType = criteriaInclude.type().equals(Void.class) ? null : criteriaInclude.getClass();
        } else {
            userDefinedType = null;
        }
    }

    public Method getMethod() {
        return method;
    }

    /**
     * Same as {@link Method#getReturnType()}
     */
    public Class<?> getMethodReturnType() {
        return methodReturnType;
    }

    public boolean isValue() {
        return isValue;
    }

    public boolean isFilter() {
        return isFilter;
    }

    public boolean isIterable() {
        return isIterable;
    }

    public boolean isArray() {
        return isArray;
    }

    /**
     * Same as {@link Method#getName()}
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return formatted method name with static rules
     */
    public String getFormattedMethodName() {
        return formattedMethodName;
    }

    public Optional<CriteriaAlias> getCriteriaAlias() {
        return Optional.ofNullable(criteriaAlias);
    }

    public Optional<String> getMethodAliasName() {
        return Optional.ofNullable(methodAliasName);
    }

    public Optional<CriteriaInclude> getCriteriaInclude() {
        return Optional.ofNullable(criteriaInclude);
    }

    /**
     * @return type from {@link CriteriaInclude#type()} but empty if is default value
     */
    public Optional<Class<?>> getUserDefinedType() {
        return Optional.ofNullable(userDefinedType);
    }
}
