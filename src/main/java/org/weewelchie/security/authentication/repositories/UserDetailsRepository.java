package org.weewelchie.security.authentication.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.weewelchie.security.authentication.model.UserDetails;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface UserDetailsRepository extends CrudRepository<UserDetails,String> {
    Optional<UserDetails> findById(String id);

    List<UserDetails> findByUserName(String userName);

    List<UserDetails> findByEMail(String email);


}
