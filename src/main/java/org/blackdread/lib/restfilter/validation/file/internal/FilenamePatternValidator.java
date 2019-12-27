package org.blackdread.lib.restfilter.validation.file.internal;

import org.blackdread.lib.restfilter.validation.file.FilenamePattern;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Code copied from {@code PatternValidator} of Hibernate, modified to fit this new constraint
 * <p>Created on 2019/12/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class FilenamePatternValidator {

    private Pattern pattern;

//    private String escapedRegexp;

    public void initialize(FilenamePattern parameters) {
        final FilenamePattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for (FilenamePattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(e);
        }

//        escapedRegexp = InterpolationHelper.escapeMessageParameter(parameters.regexp());
    }

    public boolean isValid(String filename, ConstraintValidatorContext constraintValidatorContext) {
        if (filename == null) {
            return true;
        }

//
//        if ( constraintValidatorContext instanceof HibernateConstraintValidatorContext ) {
//            constraintValidatorContext.unwrap( HibernateConstraintValidatorContext.class ).addMessageParameter( "regexp", escapedRegexp );
//        }

        Matcher m = pattern.matcher(filename);
        return m.matches();
    }

//    protected Pattern getPattern() {
//        return pattern;
//    }
}
