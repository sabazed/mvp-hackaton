package com.alfish.arealmvp.repository;

import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

}
