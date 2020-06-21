package woowa.bossdog.subway.service.path;

public class DuplicatedStationPathException extends RuntimeException {
    public DuplicatedStationPathException() {
        super("출발역과 도착역은 같을 수 없습니다.");
    }
}
