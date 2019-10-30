package org.blackdread.lib.restfilter.validation;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Created on 2019/10/29.</p>
 *
 * @author Yoann CAPLAIN
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = ValidateViaSpringTest.ValidateApp.class)
class ValidateViaSpringTest {

    private static final Logger log = LoggerFactory.getLogger(ValidateViaSpringTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ValidateApp.ServiceA serviceA;

    @Autowired
    private Validator validator;

    private MyCriteria myCriteria;

    @BeforeEach
    void setUp() {
        myCriteria = new MyCriteria();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canValidateOnMethod() {
        myCriteria.filter = new LongFilter();
        myCriteria.filter.setEquals(1L);
        Assertions.assertThrows(ConstraintViolationException.class, () -> serviceA.call(myCriteria));

        myCriteria.filter.setEquals(null);
        myCriteria.filter.setIn(List2.of(1L, 2L));
        Assertions.assertThrows(ConstraintViolationException.class, () -> serviceA.call(myCriteria));

        myCriteria.filter.setIn(null);
        myCriteria.filter.setNotIn(List2.of(1L, 2L));
        Assertions.assertThrows(ConstraintViolationException.class, () -> serviceA.call(myCriteria));

        myCriteria.filter.setNotIn(null);
        myCriteria.filter.setGreaterThan(1L);
        Assertions.assertThrows(ConstraintViolationException.class, () -> serviceA.call(myCriteria));

        myCriteria.filter.setGreaterThan(null);
        myCriteria.filter.setLessThan(1L);
        Assertions.assertThrows(ConstraintViolationException.class, () -> serviceA.call(myCriteria));

        myCriteria.filter.setLessThan(null);
        myCriteria.filter.setNotEquals(1L);
        myCriteria.filter.setGreaterThanOrEqual(1L);
        myCriteria.filter.setLessThanOrEqual(1L);
        Assertions.assertDoesNotThrow(() -> serviceA.call(myCriteria));
    }

    @Test
    void canValidateInRest() throws Exception {
        this.mockMvc.perform(get("/api/b"))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/b?filter.equals=1"))
            .andExpect(status().isBadRequest());

        this.mockMvc.perform(get("/api/b?filter.in=1,2"))
            .andExpect(status().isBadRequest());

        this.mockMvc.perform(get("/api/b?filter.notIn=1,2"))
            .andExpect(status().isBadRequest());

        this.mockMvc.perform(get("/api/b?filter.greaterThan=1"))
            .andExpect(status().isBadRequest());
    }

    @SpringBootApplication
    static class ValidateApp {

        @Service
        @Validated
        class ServiceA {
            void call(@Valid MyCriteria criteria) {
            }
        }

        @RestController
        class ValidateController {

            private final Logger log = LoggerFactory.getLogger(ValidateController.class);

            @GetMapping("/api/a")
            void a() {
            }

            @GetMapping("/api/b")
            void b(@Valid MyCriteria criteria) {
                log.info("criteria: {}", criteria);
            }

        }

    }

    static class MyCriteria {
        @EqualsForbidden
        @InForbidden
        @NotInForbidden
        @GreaterThanForbidden
        @LessThanForbidden
        LongFilter filter;

        @FilterRestrict(inverseRestrict = true)
        LongFilter allForbid;

        public LongFilter getFilter() {
            return filter;
        }

        public void setFilter(final LongFilter filter) {
            this.filter = filter;
        }

        public LongFilter getAllForbid() {
            return allForbid;
        }

        public void setAllForbid(final LongFilter allForbid) {
            this.allForbid = allForbid;
        }
    }

}
