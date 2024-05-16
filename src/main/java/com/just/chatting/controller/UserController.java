package com.just.chatting.controller;

import com.just.chatting.common.ResponseDto;
import com.just.chatting.entity.User;
import com.just.chatting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "user/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("user",new User());
        return "user/joinForm";
    }

    @PostMapping("/auth/joinProc")
    public String register(@ModelAttribute User user, Model model) {
        try {
            User createdUser = User.createUser(user, passwordEncoder);
            userService.register(createdUser);
        } catch (BadRequestException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "auth/joinForm";
        }
        return "redirect:/";
    }

    @GetMapping("/user/find")
    @ResponseBody
    public ResponseDto<Integer> findUser(@RequestParam String nickName, Model model){
        User findUser = userService.findByNickName(nickName);
        model.addAttribute("findUser",findUser);
        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }
}
