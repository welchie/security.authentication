package org.weewelchie.security.authentication.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.weewelchie.security.authentication.model.UserDetails;
import org.weewelchie.security.authentication.model.UserDetailsId;

import java.util.List;

@EnableScan
public interface UserDetailsRepository extends CrudRepository<UserDetails, UserDetailsId> {

    List<UserDetails> findByUsername(String userName);

    List<UserDetails> findByEmail(String email);

    List<UserDetails> findById(String id);


}
