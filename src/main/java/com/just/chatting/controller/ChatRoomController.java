package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.ChatRoomUser;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.service.ChatService;
import com.just.chatting.service.FriendService;
import com.just.chatting.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final FriendService friendService;

    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> checkChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        CamelCaseMap resultBox = new CamelCaseMap();
        User loginUser = principal.getUser();
        Optional<ChatRoom> chatRoom = chatService.findChatRoomByUsers(chatRoomDto.getToUserIdList(),loginUser.getId());
        if(chatRoom.isPresent()){
            resultBox.put("chatRoomIsPresent",true);
            resultBox.put("chatRoomId",chatRoom.get().getId());
        } else {
            resultBox.put("chatRoomIsPresent",false);
        }
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/rooms")
    public String chatRoomList(@AuthenticationPrincipal PrincipalDetail principal, Model model){
        List<ChatRoom> chatRoomList = chatService.findAllByUserId(principal.getUser().getId());
        model.addAttribute("chatRoomList",chatRoomList);
        model.addAttribute("target","chatRoom");
        return "index-chatroom";
    }

    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.createChatRoom(chatRoomDto.getToUserIdList(),principal.getUser().getId());
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("chatRoomId",chatRoom.getId());
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }

    @GetMapping("/room/{chatRoomId}")
    public String chatRoom(@PathVariable("chatRoomId") Integer chatRoomId, @AuthenticationPrincipal PrincipalDetail principal, Model model){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomId).orElseThrow(EntityNotFoundException::new);
        Page<ChatMessage> latestChatMessageList = chatService.findChatMessagesByChatRoomId(chatRoomId, PageRequest.of(0,10,Sort.by(Sort.Direction.DESC, "sendDt")));
        model.addAttribute("latestChatMessageList", latestChatMessageList.getContent());
        model.addAttribute("chatRoom",chatRoom);
        model.addAttribute("senderId",principal.getUser().getId());

        List<User> myFriendList = friendService.findAllFriendsOfMine(principal);
        List<ChatRoomUser> chatRoomUserList = chatRoom.getChatRoomUserList();
        List<User> userListInThisChatRoom = chatRoomUserList.stream().map(ChatRoomUser::getUser).toList();
        List<User> friendListNotInThisChatRoom = new ArrayList<>();
        for(User friend : myFriendList){
            if(!userListInThisChatRoom.contains(friend)){
               friendListNotInThisChatRoom.add(friend);
            }
        }

        model.addAttribute("friendListNotInThisChatRoom",friendListNotInThisChatRoom);
        return "chat/room";
    }

    @GetMapping("/room/{chatRoomId}/messages")
    @ResponseBody
    public List<ChatMessage> getChatMessages(@PathVariable("chatRoomId") Integer chatRoomId, @RequestParam("page") int page) {
        Page<ChatMessage> chatMessages = chatService.findChatMessagesByChatRoomId(chatRoomId, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "sendDt")));
        return chatMessages.getContent();
    }

    @PostMapping("/room/enter")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> enterChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomDto.getChatRoomId()).orElseThrow(EntityNotFoundException::new);
        ChatRoomUser chatRoomUser = chatService.findByChatRoomIdAndUserId(chatRoom,principal.getUser()).orElseThrow(EntityNotFoundException::new);
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/room/leave")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> leaveChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("success",true);
        try {
            ChatRoom chatRoom = chatService.findChatRoomById(chatRoomDto.getChatRoomId()).orElseThrow(EntityNotFoundException::new);
            ChatRoomUser chatRoomUser = chatService.findByChatRoomIdAndUserId(chatRoom, principal.getUser()).orElseThrow(EntityNotFoundException::new);
            chatService.deleteChatRoomUser(chatRoomUser);
        } catch (Exception e){
            e.printStackTrace();
            resultBox.put("success",false);
        }
        return ResponseEntity.ok(resultBox);
    }

    @PostMapping("/room/edit")
    @ResponseBody
    public ResponseEntity<CamelCaseMap> editChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal PrincipalDetail principal){
        ChatRoom chatRoom = chatService.findChatRoomById(chatRoomDto.getChatRoomId()).orElseThrow(EntityNotFoundException::new);
        chatRoom.setName(chatRoomDto.getChatRoomName());
        chatService.save(chatRoom);
        CamelCaseMap resultBox = new CamelCaseMap();
        resultBox.put("success",true);
        return ResponseEntity.ok(resultBox);
    }
}
