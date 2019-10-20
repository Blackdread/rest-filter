package org.blackdread.lib.restfilter.criteria;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.contains;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = SpringTestApp2.class)
class WebParserTest {

    private static final Logger log = getLogger(WebParserTest.class);

    @Autowired
    private MockMvc mockMvc;

    private final String instantText = "2019-10-19T15:50:10Z";
    private final Instant instant = Instant.parse(instantText);
    private final String localDateText = "2019-10-19";
    private final LocalDate localDate = LocalDate.parse(localDateText);
    private final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
    private final Duration duration = Duration.parse("P2DT20.345S");

    private final DebugController.MyDTO myDTO = new DebugController.MyDTO();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    /*
     * See beans registered (org.springframework.format.Formatter) to be able to format and accept different string entry to be able to parse it into time objects
     */

    @Test
    void allNull() throws Exception {
        this.mockMvc.perform(get("/api/test"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void instantParse() throws Exception {
        this.mockMvc.perform(get("/api/test?instantFilter.equals=2019-10-19T15:50:10Z"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter.equals").value("2019-10-19T15:50:10Z"))
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void instantParseIn() throws Exception {
        this.mockMvc.perform(get("/api/test?instantFilter.in=2019-10-19T15:50:10Z,2017-05-19T15:50:10Z"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter.in").value(contains("2019-10-19T15:50:10Z", "2017-05-19T15:50:10Z")))
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void instantParseMs() throws Exception {
        // can make this work if define parser in Formatters
        final long epochMilli = instant.toEpochMilli();
        this.mockMvc.perform(get("/api/test?instantFilter.equals=" + epochMilli))
            .andExpect(status().isBadRequest());
    }

    @Test
    void localDateParse() throws Exception {
        this.mockMvc.perform(get("/api/test?localDateFilter.equals=2019-10-19"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter.equals").value("2019-10-19"))
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void localDateParseIn() throws Exception {
        this.mockMvc.perform(get("/api/test?localDateFilter.in=2019-10-19,2017-02-19"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter.in").value(contains("2019-10-19", "2017-02-19")))
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void localDateParseMs() throws Exception {
        // can make this work if define parser in Formatters
        final long epochMilli = localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        this.mockMvc.perform(get("/api/test?localDateFilter.equals=" + epochMilli))
            .andExpect(status().isBadRequest());
    }

    @Test
    void zonedDateTimeParseDate() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=2019-10-19"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.equals").value("2019-10-19T00:00:00Z"))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTime() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=2007-12-03T10:15:30"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.equals").value("2007-12-03T10:15:30Z"))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTimeUtc() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=2007-12-03T10:15:30Z"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.equals").value("2007-12-03T10:15:30Z"))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTimeZone() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=2007-12-03T10:15:30+01:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.equals").value("2007-12-03T10:15:30+01:00"))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTimeZoneIn() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.in=2007-12-03T10:15:30+01:00,2007-05-03T10:15:30+08:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.in").value(contains("2007-12-03T10:15:30+01:00", "2007-05-03T10:15:30+08:00")))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTimeZoneIn2() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.in=2007-12-03T10:15:30+01:00&zonedDateTimeFilter.in=2007-05-03T10:15:30+08:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.in").value(contains("2007-12-03T10:15:30+01:00", "2007-05-03T10:15:30+08:00")))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseDateTimeZoneExtra() throws Exception {
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=2007-12-03T10:15:30+01:00[Europe/Paris]"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter.equals").value("2007-12-03T10:15:30+01:00"))
            .andExpect(jsonPath("$.durationFilter").hasJsonPath());
    }

    @Test
    void zonedDateTimeParseMs() throws Exception {
        // can make this work if define parser in Formatters
        final long epochMilli = zonedDateTime.toInstant().toEpochMilli();
        this.mockMvc.perform(get("/api/test?zonedDateTimeFilter.equals=" + epochMilli))
            .andExpect(status().isBadRequest());
    }

    @Test
    void durationParse() throws Exception {
        this.mockMvc.perform(get("/api/test?durationFilter.equals=P2DT20S"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter.equals").value("PT48H20S"));
    }

    @Test
    void durationParse2() throws Exception {
        this.mockMvc.perform(get("/api/test?durationFilter.equals=PT48H20M"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter.equals").value("PT48H20M"));
    }

    @Test
    void durationParsePrecise() throws Exception {
        this.mockMvc.perform(get("/api/test?durationFilter.equals=P2DT20.345S"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter.equals").value("PT48H20.345S"));
    }

    @Test
    void durationParsePreciseIn() throws Exception {
        this.mockMvc.perform(get("/api/test?durationFilter.in=P2DT20.345S,P2DT25.345S"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.instantFilter").hasJsonPath())
            .andExpect(jsonPath("$.localDateFilter").hasJsonPath())
            .andExpect(jsonPath("$.zonedDateTimeFilter").hasJsonPath())
            .andExpect(jsonPath("$.durationFilter.in").value(contains("PT48H20.345S", "PT48H25.345S")));
    }

    @Test
    void durationParseMs() throws Exception {
        // can make this work if define parser in Formatters
        this.mockMvc.perform(get("/api/test?durationFilter.equals=5000"))
            .andExpect(status().isBadRequest());
    }

}
