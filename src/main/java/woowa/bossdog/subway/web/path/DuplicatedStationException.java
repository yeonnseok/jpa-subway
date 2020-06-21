package woowa.bossdog.subway.web.path;

public class DuplicatedStationException extends RuntimeException {
    public DuplicatedStationException() {
        super("지하철역 이름이 중복되었습니다.");
    }
}
