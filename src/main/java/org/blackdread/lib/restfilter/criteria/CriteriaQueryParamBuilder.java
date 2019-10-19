package org.blackdread.lib.restfilter.criteria;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.format.DateTimeFormatter;

@NotThreadSafe
public class CriteriaQueryParamBuilder {

    private DateTimeFormatter instantFormat = DateTimeFormatter.ISO_INSTANT;

    /**
     * @param formatter formatter to use for {@link java.time.Instant} type
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withInstantFormat(final DateTimeFormatter formatter) {
        this.instantFormat = formatter;
        return this;
    }

    public CriteriaQueryParam build() {
        throw new IllegalStateException("todo");
    }

}
