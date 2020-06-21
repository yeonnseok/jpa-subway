package woowa.bossdog.subway.service.Member;

public class ExistedEmailException extends RuntimeException {
    public ExistedEmailException() {
        super("이미 등록된 이메일 입니다.");
    }
}
