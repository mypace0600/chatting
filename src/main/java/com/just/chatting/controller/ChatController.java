package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import com.just.chatting.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/check")
    public ResponseEntity<CamelCaseMap> checkChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        CamelCaseMap resultBox = new CamelCaseMap();
        User loginUser = principal.getUser();
        Optional<ChatRoom> chatRoom = chatService.findChatRoom(loginUser.getId(),chatRoomDto.getFromUserId());
        if(chatRoom.isPresent()){
            resultBox.put("success",true);
            resultBox.put("chatRoomId",chatRoom.get().getId());
        } else {
            resultBox.put("chatRoomId","");
        }
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/room")
    public ResponseEntity<CamelCaseMap> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomDto.getFromUserId(),principal.getUser().getId());
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("chatRoomId",chatRoom.getId());
        return ResponseEntity.ok(resultBox);
    }

//    @GetMapping("/room/{chatRoomId}")
//    public String chatRoom(@PathVariable("chatRoomId")Integer chatRoomId, @AuthenticationPrincipal PrincipalDetail principal){
//        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomId).orElseThrow(EntityNotFoundException::new);
//
//    }


}
