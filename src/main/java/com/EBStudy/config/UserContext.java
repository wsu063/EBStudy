package com.EBStudy.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.List;

@Getter
public class UserContext extends User {
    //authentication 객체에 저장하고 싶은 값을 필드로 지정
    private final String name;


    public UserContext(com.EBStudy.entity.User user, List<GrantedAuthority> authorities) {
        //User 생성자 실행
        super(user.getEmail(), user.getPassword(), authorities);
        // 추가 내용 입력
        this.name = user.getName();
    }
}
