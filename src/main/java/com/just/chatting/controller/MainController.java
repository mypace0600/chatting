package com.just.chatting.controller;

import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;
import com.just.chatting.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final FriendService friendService;
    @GetMapping("/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetail principalDetail) {
            List<User> friendList = friendService.findAllFriendsOfMine(principalDetail);
            Integer friendCount = friendList.size();
            model.addAttribute("friendList", friendList);
            model.addAttribute("friendCount", friendCount);
        }
        return "index-friend";
    }
}
