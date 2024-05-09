package com.EBStudy.entity;

import com.EBStudy.constant.Role;
import com.EBStudy.dto.UserFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String quiz;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private MyStudy myStudy;

    //dto -> entity
    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(userFormDto.getPassword());

        User user = new User();
        user.setName(userFormDto.getName());
        user.setEmail(userFormDto.getEmail());
        user.setQuiz(userFormDto.getQuiz());
        user.setPassword(password);
        //개발자가 지정해줘야 하는 정보
        user.setRole(Role.STUDENT);

        //나의강의를 설정한다.
        MyStudy myStudy = MyStudy.createMyStudy(user);
        user.setMyStudy(myStudy);



        return user;
    }
}
