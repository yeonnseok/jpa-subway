package woowa.bossdog.subway.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.bossdog.subway.service.Member.MemberService;
import woowa.bossdog.subway.service.Member.dto.LoginRequest;
import woowa.bossdog.subway.service.Member.dto.TokenResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class LoginMemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = memberService.createToken(request);
        return ResponseEntity.ok().body(new TokenResponse(token, "Bearer"));
    }


}
