package com.email.emailapi.repository;

import com.email.emailapi.dto.UserEmailDto;
import com.email.emailapi.model.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, Long> {


    @Query( "SELECT " +
            "NEW com.email.emailapi.dto.UserEmailDto(ue.email, ue.password) " +
            "FROM " +
            "UserEmail ue WHERE ue.user_id = 1")
    List<UserEmailDto> findByUserEmail(String email);

    @Query( "SELECT " +
            "NEW com.email.emailapi.dto.UserEmailDto(ue.email, ue.password) " +
            "FROM " +
            "UserEmail ue WHERE ue.user_id = 1")
    List<UserEmailDto> findByUserPassword(String password);


}
