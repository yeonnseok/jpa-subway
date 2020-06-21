package woowa.bossdog.subway.service.Member;

public class NotExistedEmailException extends RuntimeException {
    public NotExistedEmailException() {
        super("등록되지 않은 이메일입니다.");
    }
}
