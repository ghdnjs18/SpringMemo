package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service // Service Type의 bean 객체로 지정
//@Component // bean 객체로 지정 - IoC 컨테이너에서 자동으로 서버 구동시 bean으로 등록해줌
public class MemoService {
    private final MemoRepository memoRepository;

    // 직접적으로 IoC컨테이너에 접촉해 bean가져오기
//    public MemoService(ApplicationContext context) {
//        // 1. 'Bean' 이름으로 가져오기
//        MemoRepository memoRepository = (MemoRepository) context.getBean("memoRepository");
//        // 2. 'Bean' 클래스 형식으로 가져오기
//        emoRepository memoRepository = context.getBean(MemoRepository.class)
//        this.memoRepository = memoRepository;
//    }

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        memoRepository.save(memo);

        // Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }

    public List<MemoResponseDto> getMemosByKeyword(String keyword) {
        return memoRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // DB 수정
        memo.update(requestDto);

        return id;
    }

    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // DB 삭제
        memoRepository.delete(memo);

        return id;
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않는다.")
        );
    }


}
