package com.example.storeeverything.services;

import com.example.storeeverything.data.UserData;
import com.example.storeeverything.data.database.UsersEntity;
import com.example.storeeverything.repositories.database.RolesEntityRepository;
import com.example.storeeverything.repositories.database.UsersEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UsersEntityRepository usersEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolesEntityRepository rolesEntityRepository;

    @Override
    public void register(UserData user) throws UserAlreadyExistException {
        // Check if user already registered
        if(checkIfUserExist(user.getLogin())){
                throw new UserAlreadyExistException("User already exists for this email");
        }
        UsersEntity userEntity = new UsersEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setRoleName(rolesEntityRepository.findByRoleName("limited"));
        encodePassword(userEntity, user);
        usersEntityRepository.save(userEntity);
    }

    @Override
    public boolean checkIfUserExist(String login) {
        return usersEntityRepository.findByLogin(login) !=null;
    }

    private void encodePassword(UsersEntity userEntity, UserData user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}