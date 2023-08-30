package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.jwt.JwtUtil;
import com.sparta.memo.service.MemoService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;
    private final JwtUtil jwtUtil;

    @GetMapping("/memo")
    public String memoPage(Model model, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        model.addAttribute("username", info.getSubject());

        return "memo";
    }

    @PostMapping("/memos")
    @ResponseBody
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto);
    }

    @GetMapping("/memos")
    @ResponseBody
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    @GetMapping("/memos/contents")
    @ResponseBody
    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        return memoService.getMemosByKeyword(keyword);
    }

    @PutMapping("/memos/{id}")
    @ResponseBody
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto);
    }

    @DeleteMapping("/memos/{id}")
    @ResponseBody
    public Long deleteMemo(@PathVariable Long id) {
        return memoService.deleteMemo(id);
    }
}