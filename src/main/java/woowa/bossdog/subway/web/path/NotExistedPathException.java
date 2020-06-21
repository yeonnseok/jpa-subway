package woowa.bossdog.subway.web.path;

public class NotExistedPathException extends RuntimeException {
    public NotExistedPathException() {
        super("경로가 존재하지 않습니다.");
    }
}
