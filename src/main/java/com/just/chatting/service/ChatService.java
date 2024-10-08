package com.just.chatting.service;

import com.just.chatting.dto.UserDto;
import com.just.chatting.entity.*;
import com.just.chatting.repository.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ChatMessageReadStatusRepository chatMessageReadStatusRepository;

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
        return chatRoomUserRepository.findByChatRoomAndUser(chatRoom, user);
    }

    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoomUser(ChatRoomUser chatRoomUser) {
        chatRoomUserRepository.delete(chatRoomUser);
    }

    public void inviteFriendToChatRoom(ChatRoomUser chatRoomUser) {
        chatRoomUserRepository.save(chatRoomUser);
    }

    public List<UserDto> updatedUserList(User user , ChatRoom chatRoom){
        List<ChatRoomUser> existUserList = chatRoomUserRepository.findAllByChatRoom(chatRoom);
        List<UserDto> updatedUserList = new ArrayList<>();
        for(ChatRoomUser cru : existUserList){
            if(!cru.getUser().getId().equals(user.getId())){
                UserDto temp = new UserDto();
                temp.setId(cru.getUser().getId());
                temp.setEmail(cru.getUser().getEmail());
                temp.setNickName(cru.getUser().getNickName());
                updatedUserList.add(temp);
            }
        }
        return updatedUserList;
    }

    @Transactional
    public void markAllMessagesAsRead(ChatRoom chatRoom, User user) {
        List<ChatMessageReadStatus> unreadStatuses = chatMessageReadStatusRepository.findByChatMessageChatRoomIdAndUserIdAndIsReadFalse(chatRoom.getId(), user.getId());
        for (ChatMessageReadStatus status : unreadStatuses) {
            log.info("@@@@@@@@@@@@ {} : {} / {}",status.getUser(),status.getChatMessage(),status.isRead());
            status.setRead(true);
        }
        chatMessageReadStatusRepository.saveAll(unreadStatuses);
    }

    public int countUnreadMessages(Integer chatRoomId, Integer userId) {
        return chatMessageReadStatusRepository.countByChatMessageChatRoomIdAndUserIdAndIsReadFalse(chatRoomId, userId);
    }
}
