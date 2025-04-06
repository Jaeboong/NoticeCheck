package com.example.NoticeCheck.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notice_checks")
public class NoticeCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String noticeId;        // 공지 메시지 ID

    @Column(nullable = false)
    private LocalDateTime checkTime; // 체크 시간

    @ElementCollection
    @CollectionTable(name = "notice_check_members", joinColumns = @JoinColumn(name = "notice_check_id"))
    @Column(name = "member_name")
    private Set<String> checkedMembers = new HashSet<>();  // 체크한 멤버들의 이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }

    public Set<String> getCheckedMembers() {
        return checkedMembers;
    }

    public void setCheckedMembers(Set<String> checkedMembers) {
        this.checkedMembers = checkedMembers;
    }

    public void addCheckedMember(String memberName) {
        this.checkedMembers.add(memberName);
    }
}