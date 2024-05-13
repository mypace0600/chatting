package com.just.chatting.controller;

import com.just.chatting.common.ResponseDto;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.FriendDto;
import com.just.chatting.entity.Friend;
import com.just.chatting.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    @GetMapping("/friend/list")
    public String getFriendList(@AuthenticationPrincipal PrincipalDetail principal, Model model){
        List<Friend> friendList = friendService.findAllFriendsOfMine(principal);
        log.info("@@@@@@@@@ friendList :{}",friendList.toString());
        model.addAttribute("friendList",friendList);
        return "friend/friendList";
    }

    @PostMapping("/friend/ask")
    @ResponseBody
    public ResponseDto<Integer> askFriend(@AuthenticationPrincipal PrincipalDetail principal, @RequestBody FriendDto friendDto){
        friendService.askFriend(principal.getUser(), friendDto);
        return new ResponseDto<>(HttpStatus.OK.value(),1);
    }
}
