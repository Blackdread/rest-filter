package org.blackdread.lib.restfilter.validation;

import org.blackdread.lib.restfilter.criteria.Criteria;
import org.blackdread.lib.restfilter.criteria.CriteriaUtilTest;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
class ValidationFilterRestrictTest {

    private static final Logger log = LoggerFactory.getLogger(ValidationFilterRestrictTest.class);

    private Validator validator;

    private MyCriteria myCriteria;

    @BeforeAll
    static void setUpClass() {
    }

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        myCriteria = new MyCriteria();
    }

    @Test
    void hasViolation() {
        myCriteria.longFilter = new LongFilter();
        myCriteria.stringFilter = new StringFilter();
        CriteriaUtilTest.fillAll(myCriteria.longFilter);
        CriteriaUtilTest.fillAll(myCriteria.stringFilter);

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(16, constraintViolations.size());
        final List<String> actualConstraints = constraintViolations.stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        assertEquals(List.of("Contains filter is not allowed", "Equals filter is not allowed", "Equals filter is not allowed", "GreaterThan filter is not allowed", "GreaterThanOrEqual filter is not allowed", "In filter is not allowed", "In filter is not allowed", "LessThan filter is not allowed", "LessThanOrEqual filter is not allowed", "NotContains filter is not allowed", "NotEquals filter is not allowed", "NotEquals filter is not allowed", "NotIn filter is not allowed", "NotIn filter is not allowed", "Specified filter is not allowed", "Specified filter is not allowed"), actualConstraints);
    }

    @Test
    void hasNoViolation() {
        myCriteria.longFilter = new LongFilter();
        myCriteria.stringFilter = new StringFilter();

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(Collections.emptySet(), constraintViolations);
    }

    @Test
    void violationCanBeTranslated() {
        Locale.setDefault(Locale.FRANCE);

        myCriteria.longFilter = new LongFilter();
        myCriteria.stringFilter = new StringFilter();
        CriteriaUtilTest.fillAll(myCriteria.longFilter);
        CriteriaUtilTest.fillAll(myCriteria.stringFilter);

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(16, constraintViolations.size());
        final List<String> actualConstraints = constraintViolations.stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        assertEquals(List.of("Le filtre Contains n'est pas autorisé", "Le filtre Equals n'est pas autorisé", "Le filtre Equals n'est pas autorisé", "Le filtre GreaterThan n'est pas autorisé", "Le filtre GreaterThanOrEqual n'est pas autorisé", "Le filtre In n'est pas autorisé", "Le filtre In n'est pas autorisé", "Le filtre LessThan n'est pas autorisé", "Le filtre LessThanOrEqual n'est pas autorisé", "Le filtre NotContains n'est pas autorisé", "Le filtre NotEquals n'est pas autorisé", "Le filtre NotEquals n'est pas autorisé", "Le filtre NotIn n'est pas autorisé", "Le filtre NotIn n'est pas autorisé", "Le filtre Specified n'est pas autorisé", "Le filtre Specified n'est pas autorisé"), actualConstraints);
    }

    @Test
    void validatorAcceptNull() {
        final FilterRestrictValidator validator = new FilterRestrictValidator();
        final boolean valid = validator.isValid(null, null);
        assertTrue(valid);
    }

    private static class MyCriteria implements Criteria {

        @FilterRestrict(restrictEquals = true, restrictNotEquals = true, restrictIn = true, restrictNotIn = true, restrictGreaterThan = true, restrictGreaterThanOrEqual = true, restrictLessThan = true, restrictLessThanOrEqual = true, restrictContains = true, restrictNotContains = true, restrictSpecified = true)
        private LongFilter longFilter;

        @FilterRestrict(inverseRestrict = true)
        private StringFilter stringFilter;

        @Override
        public Criteria copy() {
            return null;
        }
    }
}
