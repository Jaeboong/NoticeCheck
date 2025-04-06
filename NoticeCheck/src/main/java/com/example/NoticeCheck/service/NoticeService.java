package com.example.NoticeCheck.service;

import com.example.NoticeCheck.entity.NoticeCheck;
import com.example.NoticeCheck.entity.Member;
import com.example.NoticeCheck.repository.NoticeCheckRepository;
import com.example.NoticeCheck.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoticeService {
    private final NoticeCheckRepository noticeCheckRepository;
    private final MemberRepository memberRepository;

    public NoticeService(NoticeCheckRepository noticeCheckRepository, MemberRepository memberRepository) {
        this.noticeCheckRepository = noticeCheckRepository;
        this.memberRepository = memberRepository;
    }

    public List<NoticeCheck> getAllNotices() {
        return noticeCheckRepository.findAll();
    }

    public Optional<NoticeCheck> getNoticeById(String noticeId) {
        return noticeCheckRepository.findByNoticeId(noticeId);
    }

    public NoticeCheck createNotice(String noticeId) {
        NoticeCheck notice = new NoticeCheck();
        notice.setNoticeId(noticeId);
        notice.setCheckTime(LocalDateTime.now());
        return noticeCheckRepository.save(notice);
    }

    public void deleteNotice(String noticeId) {
        noticeCheckRepository.findByNoticeId(noticeId).ifPresent(notice -> {
            noticeCheckRepository.delete(notice);
        });
    }

    public void checkNotice(String noticeId, String checkText) {
        noticeCheckRepository.findByNoticeId(noticeId).ifPresent(notice -> {
            // 모든 부원 목록을 가져옴
            List<Member> allMembers = memberRepository.findAll();
            Set<String> memberNames = allMembers.stream()
                .map(Member::getName)
                .collect(Collectors.toSet());

            // 체크 텍스트를 줄 단위로 분리
            String[] lines = checkText.split("\n");
            
            // 기존 체크된 멤버 목록을 가져옴
            Set<String> checkedMembers = notice.getCheckedMembers();
            if (checkedMembers == null) {
                checkedMembers = new HashSet<>();
                notice.setCheckedMembers(checkedMembers);
            }

            // 각 줄을 처리
            for (String line : lines) {
                String line_trim = line.trim();
                if (line_trim.isEmpty()) continue;

                String memberName;
                
                // 상세 정보가 포함된 줄 처리 (예: "하상우/매니저/풀,창업")
                if (line_trim.contains("/")) {
                    memberName = line_trim.split("/")[0].trim();
                } else {
                    // 단순 이름만 있는 경우
                    memberName = line_trim;
                }
                
                // 부원 DB에 있는 이름인 경우에만 추가
                if (!memberName.isEmpty() && memberNames.contains(memberName)) {
                    checkedMembers.add(memberName);
                }
            }
            
            noticeCheckRepository.save(notice);
        });
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
} 