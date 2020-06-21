package woowa.bossdog.subway.service.favorite;

public class ExistedFavoriteException extends RuntimeException {
    public ExistedFavoriteException() {
        super("이미 등록된 즐겨찾기 경로입니다.");
    }
}
