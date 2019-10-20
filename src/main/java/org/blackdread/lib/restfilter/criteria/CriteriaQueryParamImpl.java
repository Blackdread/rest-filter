package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriBuilder;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
@ThreadSafe
final class CriteriaQueryParamImpl implements CriteriaQueryParam {

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamImpl.class);

    private final Function<Boolean, String> booleanFormatter;
    private final Function<BigDecimal, String> bigDecimalFormatter;
    private final Function<Double, String> doubleFormatter;
    private final Function<Float, String> floatFormatter;
    private final Function<Instant, String> instantFormatter;
    private final Function<LocalDate, String> localDateFormatter;
    private final Function<LocalDateTime, String> localDateTimeFormatter;
    private final Function<ZonedDateTime, String> zonedDateTimeFormatter;
    private final Function<Duration, String> durationFormatter;

    CriteriaQueryParamImpl(final Function<Boolean, String> booleanFormatter, final Function<BigDecimal, String> bigDecimalFormatter, final Function<Double, String> doubleFormatter, final Function<Float, String> floatFormatter, final Function<Instant, String> instantFormatter, final Function<LocalDate, String> localDateFormatter, final Function<LocalDateTime, String> localDateTimeFormatter, final Function<ZonedDateTime, String> zonedDateTimeFormatter, final Function<Duration, String> durationFormatter) {
        this.booleanFormatter = booleanFormatter;
        this.bigDecimalFormatter = bigDecimalFormatter;
        this.doubleFormatter = doubleFormatter;
        this.floatFormatter = floatFormatter;
        this.instantFormatter = instantFormatter;
        this.localDateFormatter = localDateFormatter;
        this.localDateTimeFormatter = localDateTimeFormatter;
        this.zonedDateTimeFormatter = zonedDateTimeFormatter;
        this.durationFormatter = durationFormatter;
    }

    private static String buildParam(final String fieldName, final String filterType) {
        return fieldName + FIELD_NAME_AND_FILTER_SEPARATOR + filterType;
    }

    @Override
    public UriBuilder buildQueryParams(final Object criteria, final UriBuilder builder) {
        final Map<String, Filter> filtersByFieldName = CriteriaFieldParserUtil.build(criteria);

        return null;
    }
}
