package edu.citadel.dal;

import edu.citadel.dal.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findAccountByUsername(String username);

}
