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
public class UserDetailsAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsAuthService.class);

    @Autowired
    UserDetailsRepository userDetailsRepo;

    public List<UserDetails> findAll()
    {
        logger.info("Finding all User Records");
        return (List<UserDetails>) userDetailsRepo.findAll();
    }

    public List<UserDetails> findByID(String id) throws UserDetailsException {
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
            return userDetailsRepo.findByEmail(email);
        }
    }

    public List<UserDetails> findByUserName(String userName) throws UserDetailsException {
        if (userName.isEmpty())
            throw new UserDetailsException("User Name parameter is empty");
        else
        {
            logger.info("Finding UserDetail records by userName: {} " , userName);
            return userDetailsRepo.findByUsername(userName);
        }
    }

    public UserDetails createUser(UserDetails user) throws UserDetailsException {
        List<UserDetails> ud = userDetailsRepo.findByUsername(user.getUsername());
        if (!ud.isEmpty())
            throw new UserDetailsException("User already exists. User Not created.");
        else if(user.getEmail() == null)
        {
            throw new UserDetailsException("Email must be populated. User Not created.");
        }
        else if(user.getUsername() == null)
        {
            throw new UserDetailsException("UserNae must be populated. User Not created.");
        }
        else
        {
            return userDetailsRepo.save(user);
        }
    }

    public void deleteUser(UserDetails user) throws UserDetailsException {
        if(userDetailsRepo.findByUsername(user.getUsername()).isEmpty())
        {
            throw new UserDetailsException("User Record not found: " + user);
        }
        else
        {
           // userDetailsRepo.delete();
           userDetailsRepo.delete(user);
           //userDetailsRepo.deleteById(user.getId());
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
