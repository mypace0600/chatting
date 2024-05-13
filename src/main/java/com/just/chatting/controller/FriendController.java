package com.just.chatting.controller;

import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.entity.Friend;
import com.just.chatting.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
