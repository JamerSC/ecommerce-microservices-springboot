package com.ecommerce.user.repository;

import com.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long>,
        JpaSpecificationExecutor<User>
{
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.roles r " +
            "LEFT JOIN FETCH r.permissions " +
            "WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

//    (If your User entity does not have an email field, keep the original query
//    and verify that the username column in your database literally contains admin@mail.com.)

//    @Query("SELECT u FROM User u " +
//            "LEFT JOIN FETCH u.roles r " +
//            "LEFT JOIN FETCH r.permissions " +
//            "WHERE u.username = :identifier OR u.email = :identifier")
//    Optional<User> findByUsername(@Param("identifier") String identifier);
}
