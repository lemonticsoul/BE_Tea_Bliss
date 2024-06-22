package store.teabliss.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.dto.MemberDto;
import store.teabliss.member.dto.MemberEditDto;
import store.teabliss.member.dto.MemberPasswordDto;
import store.teabliss.member.dto.MemberResponse;
import store.teabliss.member.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 API")
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;

    /*
        관리자 영역
     */

    @GetMapping("/member-list")
    @Operation(summary = "(관리자) 회원 관리 리스트", description = "(관리자) 회원 관리 리스트")
    public ResponseEntity<MemberResponse> memberList(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname,
            @RequestParam int page,
            @RequestParam int limit
    ) {

        MemberResponse memberResponse = memberService.memberList(email, nickname, page, limit);

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "(관리자) 회원 단일 검색", description = "(관리자) 회원 단일 검색")
    public ResponseEntity<MemberResponse> memberList(
            @PathVariable(name = "id") Long id
    ) {

        MemberDto memberDto = memberService.selectMemberId2(id);

        return ResponseEntity.ok(MemberResponse.ok(memberDto));
    }

    @PutMapping("/password/{id}")
    @Operation(summary = "(관리자) 회원 비밀번호 변경", description = "(관리자) 회원 비밀번호 변경")
    public ResponseEntity<MemberResponse> updatePassword2(
            @PathVariable(name = "id") Long id,
            @RequestBody MemberPasswordDto memberPasswordDto
    ) {
        int result = memberService.updatePassword2(id, memberPasswordDto);

        return ResponseEntity.ok(MemberResponse.ok(result));
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "(관리자) 회원 정보 수정", description = "(관리자) 회원 정보 수정 API")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable(name = "id") Long id,
            @RequestBody MemberEditDto memberEditDto
    ) {

        int result = memberService.updateMember(id, memberEditDto);

        return ResponseEntity.ok(MemberResponse.ok(result));
    }
}
