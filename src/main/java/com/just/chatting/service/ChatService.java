package com.just.chatting.service;

import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.ChatRoomUser;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.repository.ChatRoomUserRepository;
import com.just.chatting.repository.ChatMessageRepository;
import com.just.chatting.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatRoom> findChatRoomByUsers(List<Integer> toUserIdList, Integer fromUserId) {
        return chatRoomRepository.findByUsers(toUserIdList,fromUserId);
    }

    public ChatRoom createChatRoom(List<Integer> toUserIdList,Integer fromUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(EntityNotFoundException::new);

        String tempChatName = fromUser.getNickName();
        for(Integer toUserId : toUserIdList) {
            User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);
            tempChatName += ",";
            tempChatName += toUser.getNickName();
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(tempChatName);
        chatRoom.setCreatedDt(Timestamp.valueOf(LocalDateTime.now()));

        chatRoom = chatRoomRepository.save(chatRoom);

        ChatRoomUser chatRoomUserFrom = new ChatRoomUser();
        chatRoomUserFrom.setChatRoom(chatRoom);
        chatRoomUserFrom.setUser(fromUser);
        chatRoomUserRepository.save(chatRoomUserFrom);

        for(Integer toUserId : toUserIdList) {
            User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);
            tempChatName += ",";
            tempChatName += toUser.getNickName();
            ChatRoomUser chatRoomUserTo = new ChatRoomUser();
            chatRoomUserTo.setChatRoom(chatRoom);
            chatRoomUserTo.setUser(toUser);
            chatRoomUserRepository.save(chatRoomUserTo);
        }

        return chatRoom;
    }

    public Optional<ChatRoom> findChatRoomById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public Page<ChatMessage> findChatMessagesByChatRoomId(Integer chatRoomId, Pageable pageable) {
        return chatMessageRepository.findByChatRoomIdOrderBySendDt(chatRoomId,pageable);
    }

    public List<ChatRoom> findAllByUserId(Integer id) {
        return chatRoomRepository.findAllByUserId(id);
    }

    public Optional<ChatRoomUser> findByChatRoomIdAndUserId(ChatRoom chatRoom,User user){
        return chatRoomUserRepository.findByChatRoomIdAndUserId(chatRoom, user);
    }

    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }
}
