package com.example.NoticeCheck.service;

import com.example.NoticeCheck.entity.Member;
import com.example.NoticeCheck.entity.NoticeCheck;
import com.example.NoticeCheck.repository.MemberRepository;
import com.example.NoticeCheck.repository.NoticeCheckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final NoticeCheckRepository noticeCheckRepository;

    public MemberService(MemberRepository memberRepository, NoticeCheckRepository noticeCheckRepository) {
        this.memberRepository = memberRepository;
        this.noticeCheckRepository = noticeCheckRepository;
    }

    public List<Member> getAllMembersWithUncheckCount() {
        List<Member> members = memberRepository.findAll();
        List<NoticeCheck> allNotices = noticeCheckRepository.findAll();

        for (Member member : members) {
            int uncheckCount = 0;
            for (NoticeCheck notice : allNotices) {
                if (!notice.getCheckedMembers().contains(member.getName())) {
                    uncheckCount++;
                }
            }
            member.setUncheckCount(uncheckCount);
        }

        return members;
    }

    public void addMembers(String names) {
        String[] nameList = names.split("[\n\r\\s]+");
        
        for (String name : nameList) {
            if (!name.trim().isEmpty()) {
                Member member = new Member();
                member.setName(name.trim());
                memberRepository.save(member);
            }
        }
    }

    public void updateMember(Long id, String name) {
        memberRepository.findById(id).ifPresent(member -> {
            member.setName(name);
            memberRepository.save(member);
        });
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public void deleteMembers(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            memberRepository.deleteAllById(ids);
        }
    }
} 