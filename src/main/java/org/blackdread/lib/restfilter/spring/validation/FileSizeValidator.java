package org.blackdread.lib.restfilter.spring.validation;

/**
 * <p>Created on 2019/10/07.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class FileSizeValidator {

//    private static final Logger log = LoggerFactory.getLogger(FileSizeValidator.class);

    private long minBytes;

    private long maxBytes;

    // todo can later add an option to be able to inject dynamic min/max bytes for CDI (spring, etc)

    public final void initialize(final FileSize constraintAnnotation) {
        minBytes = constraintAnnotation.min();
        maxBytes = constraintAnnotation.max();
        validateParameters();
    }

    private void validateParameters() {
        if (minBytes < 0)
            throw new IllegalArgumentException("Min cannot be negative");
        if (maxBytes < 0)
            throw new IllegalArgumentException("Max cannot be negative");
        if (maxBytes < minBytes)
            throw new IllegalArgumentException("Max cannot be lower than minBytes");
    }

    protected final long getMinBytes() {
        return minBytes;
    }

    protected final long getMaxBytes() {
        return maxBytes;
    }
}
