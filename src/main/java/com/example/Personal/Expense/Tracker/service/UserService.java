package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.constant.PredefinedRole;
import com.example.Personal.Expense.Tracker.dto.request.user.UserCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.user.UserUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.user.UserResponse;
import com.example.Personal.Expense.Tracker.entity.Role;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.mapper.UserMapper;
import com.example.Personal.Expense.Tracker.repository.RoleRepository;
import com.example.Personal.Expense.Tracker.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;


    public UserResponse createUser(UserCreationRequest rq){
        User user = userMapper.toUser(rq);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(rq.getPassword()));

        HashSet<Role> roles  = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        try{
            user = userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }
    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
//    public UserResponse updateUser(String userId, UserUpdateRequest rq){
//        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
//        userMapper.
//    }
}
