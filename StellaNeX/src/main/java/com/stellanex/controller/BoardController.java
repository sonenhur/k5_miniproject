package com.stellanex.controller;

import com.stellanex.domain.Board;
import com.stellanex.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/getBoardList")
    public String getBoardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "getBoardList";
    }

    @GetMapping("/getBoard")
    public String getBoard(Long seq, Model model) {
        model.addAttribute("board", boardService.getBoard(seq));
        return "getBoard";
    }

    @GetMapping("/insertBoard")
    public String insertBoard(Board board, Model model) {
        model.addAttribute("insert", boardService.insertBoard(board));
        return "insertBoard";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(Board board, Model model) {
        model.addAttribute("update", boardService.updateBoard(board));
        return "updateBoard";
    }

    @DeleteMapping("/deleteBoard")
    public String deleteBoard(Board board, Model model) {
        model.addAttribute("delete", boardService.deleteBoard(board));
        return "deleteBoard";
    }

    @GetMapping("/hello")
    public void hello(Model model) {
        model.addAttribute("greeting", "Hello 타임리프. ^^");
    }

    @GetMapping("/hello1")
    public String hello1(Model model) {
        model.addAttribute("greeting", "Hello 타임리프. ^^");
        return "hello";
    }

    @GetMapping("/hello2")
    public ModelAndView hello2() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("greeting", "Hello 타임리프. ^^");
        mv.setViewName("hello");
        return mv;
    }
}