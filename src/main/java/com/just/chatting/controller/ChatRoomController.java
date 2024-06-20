package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;



    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> checkChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        CamelCaseMap resultBox = new CamelCaseMap();
        User loginUser = principal.getUser();
        Optional<ChatRoom> chatRoom = chatService.findChatRoomByUsers(chatRoomDto.getToUserId(),loginUser.getId());
        if(chatRoom.isPresent()){
            resultBox.put("success",true);
            resultBox.put("chatRoomId",chatRoom.get().getId());
        }
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomDto.getToUserId(),principal.getUser().getId());
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("chatRoomId",chatRoom.getId());
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/room/{chatRoomId}")
    public String chatRoom(@PathVariable("chatRoomId") Integer chatRoomId, @AuthenticationPrincipal PrincipalDetail principal, Model model){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("chatRoom",chatRoom);
        return "chat/room";
    }
}
