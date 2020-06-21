package woowa.bossdog.subway.web.station;

public class NotExistedStationException extends RuntimeException {
    public NotExistedStationException() {
        super("존재하지 않은 지하철 역입니다.");
    }
}
