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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
}
