package com.example.NoticeCheck.controller;

import com.example.NoticeCheck.entity.NoticeCheck;
import com.example.NoticeCheck.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "home";
    }

    @GetMapping("/notice/{noticeId}")
    public String viewNotice(@PathVariable String noticeId, Model model) {
        String decodedNoticeId = UriUtils.decode(noticeId, StandardCharsets.UTF_8.name());
        noticeService.getNoticeById(decodedNoticeId).ifPresent(notice -> {
            model.addAttribute("notice", notice);
            model.addAttribute("allMembers", noticeService.getAllMembers());
        });
        return "notice-detail";
    }

    @PostMapping("/notice/create")
    public String createNotice(@RequestParam String noticeId) {
        NoticeCheck notice = noticeService.createNotice(noticeId);
        String encodedNoticeId = UriUtils.encode(noticeId, StandardCharsets.UTF_8.name());
        return "redirect:/notice/" + encodedNoticeId;
    }

    @PostMapping("/notice/delete/{noticeId}")
    public String deleteNotice(@PathVariable String noticeId) {
        String decodedNoticeId = UriUtils.decode(noticeId, StandardCharsets.UTF_8.name());
        noticeService.deleteNotice(decodedNoticeId);
        return "redirect:/";
    }

    @PostMapping("/notice/check")
    public String checkNotice(@RequestParam String noticeId, @RequestParam String checkText) {
        noticeService.checkNotice(noticeId, checkText);
        String encodedNoticeId = UriUtils.encode(noticeId, StandardCharsets.UTF_8.name());
        return "redirect:/notice/" + encodedNoticeId;
    }
} 