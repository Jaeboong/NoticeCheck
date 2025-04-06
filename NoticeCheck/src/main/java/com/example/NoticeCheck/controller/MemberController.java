package com.example.NoticeCheck.controller;

import com.example.NoticeCheck.entity.Member;
import com.example.NoticeCheck.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String listMembers(Model model) {
        List<Member> members = memberService.getAllMembersWithUncheckCount();
        model.addAttribute("members", members);
        model.addAttribute("newMember", new Member());
        return "members";
    }

    @PostMapping("/add")
    public String addMember(@RequestParam String names) {
        memberService.addMembers(names);
        return "redirect:/members";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable Long id, @RequestParam String name) {
        memberService.updateMember(id, name);
        return "redirect:/members";
    }

    @PostMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }

    @PostMapping("/bulk-delete")
    public String bulkDeleteMembers(@RequestParam(value = "selectedIds", required = false) List<Long> selectedIds) {
        memberService.deleteMembers(selectedIds);
        return "redirect:/members";
    }
} 