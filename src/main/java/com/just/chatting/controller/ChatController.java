package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatMessageRequestDto;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.repository.UserRepository;
import com.just.chatting.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping("/chat/check")
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

    @PostMapping("/chat/room")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomDto.getToUserId(),principal.getUser().getId());
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("chatRoomId",chatRoom.getId());
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/chat/room/{chatRoomId}")
    public String chatRoom(@PathVariable("chatRoomId") Integer chatRoomId, @AuthenticationPrincipal PrincipalDetail principal, Model model){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("chatRoom",chatRoom);
        return "chat/room";
    }

//    @MessageMapping("/chat/rooms/{chatRoomId}/send") // 클라이언트에서 서버로 보낸 메시지를 메시지를 라우팅
//    @SendTo("/topic/public/rooms/{chatRoomId}") // 구독한 클라이언트에게 response를 제공할 url 정의
//    @ResponseBody
//    public ChatMessage sendMessage(@DestinationVariable("chatRoomId") int chatRoomId, @Payload ChatMessageRequestDto message) {
//        // @DestinationVariable : 구독 및 메시징의 동적 url 변수를 설정. RestAPI의 @PathValue와 같다
//        // @Payload: 메시지의 body를 정의한 객체에 매핑합니다
//        User sender = userRepository.findById(message.fromId()).orElseThrow(EntityNotFoundException::new);
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new);
//
//        ChatMessage chatMessageCreateCommand = new ChatMessage();
//        chatMessageCreateCommand.setContent(message.content());
//        chatMessageCreateCommand.setSender(sender);
//        chatMessageCreateCommand.setChatRoom(chatRoom);
//
//        return chatService.saveMessage(chatMessageCreateCommand);
//    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
