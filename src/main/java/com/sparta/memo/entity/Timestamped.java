package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 해당 클레스에 자동 시간을 넣어준다.
public abstract class Timestamped {

    @CreatedDate // 최초 생성 시간 저장
    @Column(updatable = false) // 수정 불가
    @Temporal(TemporalType.TIMESTAMP) // 시간 타입을 매핑할 때 사용.
    private LocalDateTime createdAt;

    @LastModifiedDate // 변경된 시간 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}