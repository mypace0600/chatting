package com.just.chatting.service;

import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.FriendDto;
import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;
import com.just.chatting.repository.FriendRepository;
import com.just.chatting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<Friend> findAllFriendsOfMine(PrincipalDetail principal) {
        return friendRepository.findAllFriendsOfMine(principal.getUser());
    }

    public void askFriend(User fromUser, FriendDto friendDto) {
        User toUser = userRepository.findById(friendDto.getToUserId()).orElseThrow(EntityNotFoundException::new);
        Friend friend = new Friend();
        friend.setFromUser(fromUser);
        friend.setToUser(toUser);
        friendRepository.save(friend);
    }
}
