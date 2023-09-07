package org.weewelchie.security.authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weewelchie.security.authentication.exception.UserDetailsException;
import org.weewelchie.security.authentication.model.UserDetails;
import org.weewelchie.security.authentication.repositories.UserDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    UserDetailsRepository userDetailsRepo;

    public List<UserDetails> findAll()
    {
        logger.info("Finding all User Records");
        return (List<UserDetails>) userDetailsRepo.findAll();
    }

    public Optional<UserDetails> findByID(String id) throws UserDetailsException {
        if (id.isEmpty())
            throw new UserDetailsException("ID parameter is empty");
        else
        {
            logger.info("Finding UserDetail records by ID: {} " , id);
            return userDetailsRepo.findById(id);
        }
    }

    public List<UserDetails> findByEmail(String email) throws UserDetailsException {
        if (email.isEmpty())
            throw new UserDetailsException("Email parameter is empty");
        else
        {
            logger.info("Finding UserDetail records by Email: {} " , email);
            return userDetailsRepo.findByEMail(email);
        }
    }

    public UserDetails createUser(UserDetails user) throws UserDetailsException {
        if(user.getEmail() == null)
        {
            throw new UserDetailsException("Email must be populated. User Not crated.");
        }
        else
        {
            return userDetailsRepo.save(user);
        }
    }

    public void deleteUser(UserDetails user) throws UserDetailsException {
        if(userDetailsRepo.findById(user.getId()).isEmpty())
        {
            throw new UserDetailsException("User Record not found: " + user);
        }
        else
        {
            userDetailsRepo.delete(user);
        }
    }

    public UserDetails updateUser(UserDetails user) throws UserDetailsException {
        if(userDetailsRepo.findById(user.getId()).isEmpty())
        {
            throw new UserDetailsException("User Record not found: " + user);
        }
        else
        {
            return userDetailsRepo.save(user);
        }
    }
}
