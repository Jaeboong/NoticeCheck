package com.example.NoticeCheck.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;            // 디스코드 닉네임

    @Transient  // DB에 저장되지 않는 임시 필드
    private int uncheckCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUncheckCount() {
        return uncheckCount;
    }

    public void setUncheckCount(int uncheckCount) {
        this.uncheckCount = uncheckCount;
    }
}