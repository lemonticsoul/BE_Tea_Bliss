package store.teabliss.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.dto.*;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.member.service.MemberService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
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

    @GetMapping("/info")
    @Operation(summary = "회원 정보 요청", description = "회원 정보 API")
    public ResponseEntity<MemberResponse> getProfile(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {

        MemberDto memberDto = memberService.selectMemberId(memberDetails.getMemberId());

        return ResponseEntity.ok(MemberResponse.ok(memberDto));
    }

    @PatchMapping("/edit")
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 API")
    public ResponseEntity<MemberResponse> updateMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberEditDto memberEditDto
    ) {

        int result = memberService.updateMember(memberDetails.getMemberId(), memberEditDto);

        return ResponseEntity.ok(MemberResponse.ok(result));
    }

    @PatchMapping("/password")
    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호 수정 API")
    public ResponseEntity<MemberResponse> updatePassword(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberPasswordDto memberPasswordDto
    ) {

        int result = memberService.updatePassword(memberDetails.getMemberId(), memberPasswordDto);

        return ResponseEntity.ok(MemberResponse.ok(result));
    }

    @PatchMapping("/address")
    @Operation(summary = "회원 주소 수정 요청", description = "회원 주소 수정 요청 API")
    public ResponseEntity<MemberResponse> updateAddress(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberAddressDto memberAddressDto
    ) {
        int result = memberService.updateAddress(memberDetails.getMemberId(), memberAddressDto);

        return ResponseEntity.ok(MemberResponse.ok(result));
    }

    /*
        관리자 영역
     */

    @GetMapping("/member-list")
    @Operation(summary = "관리자 회원 관리 리스트", description = "회원 관리 리스트")
    public ResponseEntity<MemberResponse> memberList(
            @RequestParam String email,
            @RequestParam String nickname,
            @RequestParam int page,
            @RequestParam int limit
    ) {

        MemberResponse memberResponse = memberService.memberList(email, nickname, page, limit);

        return ResponseEntity.ok(memberResponse);
    }

}
