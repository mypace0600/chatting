package com.just.chatting.service;

import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.entity.User;
import com.just.chatting.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkEmailDuplicated(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Transactional
    public void register(User user) throws BadRequestException {
        if(!checkEmailDuplicated(user.getEmail())){
            userRepository.save(user);
        } else {
            throw new BadRequestException("이미 사용중인 이메일 입니다.");
        }
    }

    public Optional<User> findByNickName(String nickName) {
        Optional<User> findUser = userRepository.findByNickName(nickName);
        return findUser;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User edit(User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        findUser.setNickName(user.getNickName());
        User updatedUser = userRepository.save(findUser);

        PrincipalDetail principalDetail = new PrincipalDetail(updatedUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities())
        );

        return updatedUser;
    }
}
