package com.just.chatting.config.security;

import com.just.chatting.entity.User;
import com.just.chatting.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService  implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User principal = userRepository.findByEmail(email).orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+email);
        });
        return new PrincipalDetail(principal);
    }
}
