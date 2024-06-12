package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import com.just.chatting.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat/check")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> checkChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        CamelCaseMap resultBox = new CamelCaseMap();
        User loginUser = principal.getUser();
        log.debug("@@@@@@@@@@@@@@@@@@ chatRoomDto.getToUserId() : {}", chatRoomDto.getToUserId());
        Optional<ChatRoom> chatRoom = chatService.findChatRoom(chatRoomDto.getToUserId(),loginUser.getId());
        if(chatRoom.isPresent()){
            resultBox.put("success",true);
            resultBox.put("chatRoomId",chatRoom.get().getId());
        } else {
            resultBox.put("chatRoomId","");
        }
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/chat/room")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomDto.getToUserId(),principal.getUser().getId());
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("chatRoomId",chatRoom.getId());
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }

    @MessageMapping("/chat/{chatRoomId}") // 클라이언트로부터 메시지를 받는 엔드포인트
    @SendTo("/topic/messages/{chatRoomId}") // 서버에서 클라이언트로 메시지를 전송할 경로 설정
    @ResponseBody
    public ChatMessage handleMessage(@PathVariable("chatRoomId") int chatRoomId, ChatMessage message) {
        // 받은 메시지를 처리하고 전달
        return chatService.saveMessage(chatRoomId, message);
    }

    @GetMapping("/chat/room/{chatRoomId}")
    public String chatRoom(@PathVariable("chatRoomId")Integer chatRoomId, @AuthenticationPrincipal PrincipalDetail principal, Model model){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("chatRoom",chatRoom);
        return "chat/room";
    }


}
