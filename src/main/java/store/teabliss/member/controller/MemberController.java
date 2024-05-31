package store.teabliss.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.dto.MemberSignUpDto;
import store.teabliss.member.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Integer> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {

        int memId = memberService.createMember(memberSignUpDto.toEntity());

        return ResponseEntity.ok(memId);
    }

    @GetMapping("/sign-in")
    public ResponseEntity<String> signIn() {
        return ResponseEntity.ok("");
    }

}
