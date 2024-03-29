package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Model.UsersEntity;
import com.example.tiketku_finalproject.Repository.UsersRepository;
import com.example.tiketku_finalproject.Response.UserSearchByTokenResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository R;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public Page<UsersEntity> getAll(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return R.findAll(pageable);
    }
    public UsersEntity getById(UUID id_user){
        return R.findById(id_user).get();
    }

    public UsersEntity updateUser(UsersEntity param) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        UsersEntity userExist =  R.findById(param.getUuid_user()).get();
        userExist.setEmail(param.getEmail());
        userExist.setPassword(passwordEncoder.encode(param.getPassword()));
        userExist.setGender(param.getGender());
        userExist.setFull_name(param.getFull_name());
        userExist.setPhone(param.getPhone());
        userExist.setModified_at(currentDateTime);
        return R.save(userExist);
    }
    public UsersEntity updateToken(UsersEntity param){
        UsersEntity userExist =  R.findByEmail(param.getEmail()).get();
        userExist.setToken(jwtService.generateToken(param.getEmail()));
        return R.save(userExist);
    }

    public UsersEntity addUsers(UsersEntity param) {
        Optional<UsersEntity> userEmailExist = R.findByEmail(param.getEmail());
        Optional<UsersEntity> userPhoneExist = R.findByPhone(param.getPhone());
        if (userEmailExist.isPresent()) {
            throw new RuntimeException("Email " + param.getEmail() + " Sudah Ada");
        }
        if(userPhoneExist.isPresent()){
            throw new RuntimeException("Nomer Telepon " + param.getPhone() + " Sudah Ada");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        param.setUuid_user(generateUUID());
        param.setCreated_at(currentDateTime);
        param.setPassword(passwordEncoder.encode(param.getPassword()));
        param.setRoles("ROLE_BUYER");
        return R.save(param);

    }
    public UsersEntity delUser(UUID param){
        UsersEntity delete = R.findById(param).get();
        R.deleteById(param);
        return delete;
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    public UsersEntity resetPassword(String email, String newPassword) {
        UsersEntity user = R.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User dengan username " + email + " tidak ditemukan"));
        UsersEntity userExist =  R.findByEmail(email).get();
        userExist.setToken(jwtService.generateToken(userExist.getEmail()));
        R.save(userExist);

        // Memeriksa apakah password baru sama dengan password sebelumnya
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("Password baru tidak boleh sama dengan password sebelumnya");
        }

        // Mengubah password baru dan menyimpan perubahan
        user.setPassword(passwordEncoder.encode(newPassword));
        return R.save(user);
    }

    public UserSearchByTokenResponse searchToken(String token){
        UsersEntity user = R.findByUserToken(token);
        UserSearchByTokenResponse userResponse = new UserSearchByTokenResponse();
        userResponse.setUuid_user(user.getUuid_user());
        userResponse.setEmail(user.getEmail());
        userResponse.setFull_name(user.getFull_name());
        userResponse.setGender(user.getGender());
        userResponse.setPhone(user.getPhone());
        userResponse.setRoles(user.getRoles());
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setModified_at(user.getModified_at());
        return userResponse;
    }
}
