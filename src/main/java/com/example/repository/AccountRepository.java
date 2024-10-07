package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    // Find user by username (may or may not exist)
    Optional<Account> findByUsername(String username);

    // Find by username and password
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
