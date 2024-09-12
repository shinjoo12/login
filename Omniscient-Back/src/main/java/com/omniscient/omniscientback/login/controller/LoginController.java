package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.JwtTokenDTO;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.JwtService;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin(origins = "http://localhost:8083")
public class LoginController {

    private final SignupService signupService;
    private final JwtService jwtService;

    public LoginController(SignupService signupService, JwtService jwtService) {
        this.signupService = signupService;
        this.jwtService = jwtService;
    }

    //로그인
    @PostMapping("/post")
    public ResponseEntity<String> login(@RequestBody SignupDTO signupDTO) {
        //SignupDTO 에서 사용자명과 패스워드 가져와 인증 시도
        boolean isAuthenticated = signupService.authenticate(signupDTO.getUserId(), signupDTO.getPassword());

        // 인증에 성공한다면
        if (isAuthenticated) {
            // 사용자 ID를 통해 JWT 토큰 생성
            String accessToken = jwtService.createAccessToken(signupDTO.getUserId());
            String refreshToken = jwtService.createRefreshJwt(signupDTO.getUserId());

            // 토큰과 만료 시간을 포함한 DTO 생성
            JwtTokenDTO tokenDTO = new JwtTokenDTO(
                    accessToken,
                    refreshToken,
                    jwtService.getAccessTokenExpiry(),
                    jwtService.getRefreshTokenExpiry()
            );

            return ResponseEntity.ok(accessToken); // JWT 토큰을 클라이언트에 반환
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 아이디 또는 비밀번호입니다");
        }
    }


}
