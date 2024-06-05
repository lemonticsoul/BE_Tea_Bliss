package store.teabliss.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.dto.MemberResponse;
import store.teabliss.member.dto.MemberSignInDto;
import store.teabliss.member.dto.MemberSignUpDto;
import store.teabliss.member.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!");
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입", description = "회원 등록 API")
    public ResponseEntity<Long> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {

        long memId = memberService.createMember(memberSignUpDto);

        return ResponseEntity.ok(memId);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "회원 로그인 API")
    public ResponseEntity<MemberResponse> signIn(@RequestBody MemberSignInDto memberSignInDto) {
        return ResponseEntity.ok(MemberResponse.ok(""));
    }

}
