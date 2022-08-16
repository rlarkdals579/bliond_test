package com.likelion.bliond.controller;

import com.likelion.bliond.dto.PollDto;
import com.likelion.bliond.service.PollService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("poll")
public class PollController {
    private PollService pollService;


    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<PollDto> pollList = pollService.getPolllist(pageNum);
        Integer[] pageList = pollService.getPageList(pageNum);

        model.addAttribute("pollList", pollList);
        model.addAttribute("pageList", pageList);

        return "poll/list";
    }


    @GetMapping("/post")
    public String write() {
        return "poll/write";
    }

    @PostMapping("/post")
    public String write(PollDto pollDto) {
        pollService.savePost(pollDto);
        return "redirect:/poll/list";
    }


    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        PollDto pollDTO = pollService.getPost(no);

        model.addAttribute("pollDto", pollDTO);
        return "poll/detail";
    }


    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        PollDto pollDTO = pollService.getPost(no);

        model.addAttribute("pollDto", pollDTO);
        return "poll/update";
    }


    @PutMapping("/post/edit/{no}")
    public String update(PollDto pollDTO) {
        pollService.savePost(pollDTO);

        return "redirect:/poll/list";
    }

    // 게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        pollService.deletePost(no);

        return "redirect:/poll/list";
    }


    @GetMapping("/poll/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<PollDto> pollDtoList = pollService.searchPosts(keyword);

        model.addAttribute("pollList", pollDtoList);

        return "poll/list";
    }
}
