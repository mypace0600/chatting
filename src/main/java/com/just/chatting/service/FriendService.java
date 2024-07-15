package com.just.chatting.service;

import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;
import com.just.chatting.repository.jpa.FriendRepository;
import com.just.chatting.repository.jpa.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<User> findAllFriendsOfMine(PrincipalDetail principal) {
        List<Friend> friendList = friendRepository.findAllFriendsOfMine(principal.getUser());
        List<User> result = new ArrayList<>();
        for(Friend f : friendList){
            if(f.getFromUser().getId().equals(principal.getUser().getId())){
                User toUser = f.getToUser();
                result.add(toUser);
            } else if(f.getToUser().getId().equals(principal.getUser().getId())){
                User fromUser = f.getFromUser();
                result.add(fromUser);
            }
        }
        result.sort(Comparator.comparing(User::getNickName));
        return result;
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
