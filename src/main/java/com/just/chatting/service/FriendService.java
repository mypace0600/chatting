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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<Friend> findAllFriendsOfMine(PrincipalDetail principal) {
        return friendRepository.findAllFriendsOfMine(principal.getUser());
    }

    @Transactional
    public void askFriend(User fromUser, Integer toUserId) {
        User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);
        Friend friend = new Friend();
        friend.setFromUser(fromUser);
        friend.setToUser(toUser);
        friend.setAreWeFriend(false);
        friendRepository.save(friend);
    }

    public List<Friend> findAllByToUser(User user) {
        List<Friend> result = friendRepository.findAllByToUser(user);
        return result;
    }

    @Transactional
    public void yesWeAreFriend(User user, int fromUserId) {
        Friend friend = friendRepository.findByToUserAndFromUser(user.getId(),fromUserId).orElseThrow(EntityNotFoundException::new);
        friend.setAreWeFriend(true);
        friendRepository.save(friend);
    }

    public List<Friend> findAllByFromUser(User user) {
        List<Friend> result = friendRepository.findAllByFromUser(user);
        return result;
    }

    @Transactional
    public void cancelRequest(User user, int toUserId) {
        Friend friend = friendRepository.findByToUserAndFromUser(toUserId,user.getId()).orElseThrow(EntityNotFoundException::new);
        if(!friend.isAreWeFriend()){
            friendRepository.deleteById(friend.getId());
        }
    }
}
