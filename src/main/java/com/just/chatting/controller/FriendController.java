package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.common.ResponseDto;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.FriendDto;
import com.just.chatting.dto.FriendSearchDto;
import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;
import com.just.chatting.service.FriendService;
import com.just.chatting.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/friend/search")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> searchFriend(@RequestBody FriendSearchDto searchDto){
        CamelCaseMap resultBox = new CamelCaseMap();
        User searchedUser = userService.findByNickName(searchDto.getSearchValue()).orElseThrow(EntityNotFoundException::new);
        resultBox.put("searchedUser",searchedUser);
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/friend/ask")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> askFriend(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody FriendDto friendDto){
        CamelCaseMap resultBox = new CamelCaseMap();
        try {
            friendService.askFriend(principal.getUser(), friendDto.getToUserId());
            resultBox.put("success",true);
        } catch (Exception e){
            e.printStackTrace();
            resultBox.put("success",false);
        }
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/friend/to-me-list")
    public String receivedList(@AuthenticationPrincipal PrincipalDetail principal, Model model){
        List<Friend> friendList = friendService.findAllByToUser(principal.getUser());
        model.addAttribute("friendList",friendList);
        return "friend/toMeList";
    }

    @PostMapping("/friend/approve")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> approveFriend(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody FriendDto friendDto){
        CamelCaseMap resultBox = new CamelCaseMap();
        User user = principal.getUser();
        try {
            friendService.yesWeAreFriend(user, friendDto.getFromUserId());
            resultBox.put("success",true);
        } catch (Exception e){
            e.printStackTrace();
            resultBox.put("success",false);
        }
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/friend/from-me-list")
    public String requestedList(@AuthenticationPrincipal PrincipalDetail principal, Model model){
        List<Friend> friendList = friendService.findAllByFromUser(principal.getUser());
        model.addAttribute("friendList",friendList);
        return "friend/fromMeList";
    }

    @PostMapping("/friend/approve-cancel")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> approveCancelFriend(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody FriendDto friendDto){
        CamelCaseMap resultBox = new CamelCaseMap();
        User user = principal.getUser();
        try {
            friendService.yesWeAreFriend(user, friendDto.getFromUserId());
            resultBox.put("success",true);
        } catch (Exception e){
            e.printStackTrace();
            resultBox.put("success",false);
        }
        return ResponseEntity.ok(resultBox);
    }
}
