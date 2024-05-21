package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.common.ResponseDto;
import com.just.chatting.entity.User;
import com.just.chatting.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
    @ResponseBody
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        log.debug("@@@@@@@@@@@@@@@@@@ user :{}", user.toString());
        Map<String, Object> response = new HashMap<>();
        try {
            User createdUser = User.createUser(user, passwordEncoder);
            userService.register(createdUser);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/find")
    @ResponseBody
    public ResponseDto<Integer> findUser(@RequestParam String nickName, Model model){
        Optional<User> findUser = userService.findByNickName(nickName);
        if(findUser.isPresent()) {
            model.addAttribute("findUser", findUser);
            return new ResponseDto<>(HttpStatus.OK.value(), 1);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping("/user/check-email")
    @ResponseBody
    public ResponseDto<Integer> checkEmail(@RequestBody CamelCaseMap map){
        log.debug("@@@@@@@@@@@@@@@@ email :{}",map.get("email"));
        String email = String.valueOf(map.get("email"));
        Optional<User> findUser = userService.findByEmail(email);
        log.debug("@@@@@@@@@@@@@@@@ findUser :{}",findUser);
        if(findUser.isPresent()){
            return new ResponseDto<>(HttpStatus.CONFLICT.value(), 2);
        }
        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }

    @PostMapping("/user/check-nickname")
    @ResponseBody
    public ResponseDto<Integer> checkNickname(@RequestBody CamelCaseMap map){
        log.debug("@@@@@@@@@@@@@@@@ nickName :{}",map.get("nickName"));
        String nickName = String.valueOf(map.get("nickName"));
        Optional<User> findUser = userService.findByNickName(nickName);
        log.debug("@@@@@@@@@@@@@@@@ findUser :{}",findUser);
        if(findUser.isPresent()){
            return new ResponseDto<>(HttpStatus.CONFLICT.value(), 2);
        }
        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }
}
