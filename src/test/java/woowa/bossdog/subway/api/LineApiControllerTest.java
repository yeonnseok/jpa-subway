package woowa.bossdog.subway.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import woowa.bossdog.subway.domain.Line;
import woowa.bossdog.subway.service.line.LineService;
import woowa.bossdog.subway.service.line.dto.LineResponse;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LineApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private LineService lineService;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("지하철 노선 생성")
    @Test
    void create() throws Exception {
        // given
        final Line line = new Line("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        given(lineService.createLine(any())).willReturn(LineResponse.from(line));

        // when
        mvc.perform(post("/lines")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"2호선\",\"startTime\":\"05:30\",\"endTime\":\"23:30\",\"intervalTime\":10}"))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", "/lines/" + line.getId()));
        // then
        verify(lineService).createLine(any());
    }

    @DisplayName("지하철 노선 목록 조회")
    @Test
    void list() throws Exception {
        // given
        final List<LineResponse> lines = new ArrayList<>();
        lines.add(LineResponse.from(new Line("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10)));
        lines.add(LineResponse.from(new Line("3호선", LocalTime.of(5, 50), LocalTime.of(23, 50), 12)));
        given(lineService.listLines()).willReturn(lines);

        // when
        mvc.perform(get("/lines"))
                .andExpect(status().isOk());

        // then
        verify(lineService).listLines();
    }

    @DisplayName("지하철 노선 단건 조회")
    @Test
    void find() throws Exception {
        // given
        final Line line = new Line(63L, "2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        given(lineService.findLine(any())).willReturn(LineResponse.from(line));

        // when
        mvc.perform(get("/lines/" + line.getId()))
                .andExpect(status().isOk());

        // then
        verify(lineService).findLine(eq(63L));
    }

    @DisplayName("지하철 노선 정보 수정")
    @Test
    void update() throws Exception {
        // given
        final Line line = new Line(63L, "2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);

        // when
        mvc.perform(put("/lines/" + line.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"신분당선\",\"startTime\":\"06:30\",\"endTime\":\"23:30\",\"intervalTime\":15}"))
                .andExpect(status().isOk());

        // then
        verify(lineService).updateLine(eq(63L), any());
    }

    @DisplayName("지하철 역 삭제")
    @Test
    void remove() throws Exception {
        // given
        final Line line = new Line(63L, "2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);

        // when
        mvc.perform(delete("/lines/" + line.getId()))
                .andExpect(status().isNoContent());

        // then
        verify(lineService).deleteLine(eq(63L));
    }


}