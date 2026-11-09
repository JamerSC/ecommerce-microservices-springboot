package com.ecommerce.user.repository;

import com.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends
        JpaRepository<User, Long>,
        JpaSpecificationExecutor<User>
{
    //
}
