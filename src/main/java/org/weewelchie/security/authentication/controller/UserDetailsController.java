package org.weewelchie.security.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.weewelchie.security.authentication.exception.UserDetailsException;
import org.weewelchie.security.authentication.model.UserDetails;
import org.weewelchie.security.authentication.service.UserDetailsAuthService;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

    private  static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

    private static final String TABLE_NAME = "UserDetails";

    private static final String ERROR_TITLE= "Error";

    @Autowired
    UserDetailsAuthService service;


    @GetMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "firstName") String firstName,
                                         @RequestParam(value = "lastName") String lastName,
                                         @RequestParam(value = "password") String password,
                                         @RequestParam(value = "email") String email
    )
    {

        try {
            String expiryDate = String.valueOf(Calendar.getInstance().getTime());

            //Hash password before storing.
            PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
;
            UserDetails ud = new UserDetails(userName,firstName,lastName,bCryptPasswordEncoder.encode(password),email,expiryDate);
            logger.info("Creating new record: {}" , ud);

            Map<String, UserDetails> response = new HashMap<>(1);
            UserDetails result = service.createUser(ud);

            response.put(TABLE_NAME, result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            Map<String, List<String>> response = new HashMap<>(1);
            List<String> errors = new ArrayList<>();
            errors.add(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
            response.put(ERROR_TITLE, errors);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> findAll()
    {
        try {
            logger.info("Getting all data....");
            Map<String, List<UserDetails>> response = new HashMap<>(1);
            List<UserDetails> result = service.findAll();
            response.put(TABLE_NAME,result);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            Map<String, List<String>> response = new HashMap<>(1);
            List<String> errors = new ArrayList<>();
            errors.add(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
            response.put(ERROR_TITLE, errors);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{userName}")
    public ResponseEntity<?> deleteByUserName(@PathVariable final String userName)
    {
        logger.info("Deleting UserDetails....");
        try {
            //Find user object
            List<UserDetails> ud = service.findByUserName(userName);
            if (ud.isEmpty())
            {
                throw new UserDetailsException("User: " + userName + " not found. Unable to delete");
            }
            logger.info("Deleting user: {}",ud.get(0) );
            service.deleteUser(ud.get(0));

            Map<String, List<String>> response = new HashMap<>(1);
            List<String> result = new ArrayList<>();
            result.add("User deleted: " + userName);
            response.put(ERROR_TITLE, result);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            Map<String, List<String>> response = new HashMap<>(1);
            List<String> errors = new ArrayList<>();
            errors.add(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
            response.put(ERROR_TITLE, errors);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
