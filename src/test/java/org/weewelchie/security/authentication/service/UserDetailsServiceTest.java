package org.weewelchie.security.authentication.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.weewelchie.security.authentication.exception.UserDetailsException;
import org.weewelchie.security.authentication.model.UserDetails;
import org.weewelchie.security.authentication.repositories.UserDetailsRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepositoryMock;

    @InjectMocks
    private UserDetailsService service;

    @Mock
    private UserDetails userDetailsMock;

    @Mock
    private UserDetailsService userDetailsServiceMock;

    private static final String ID = "test-ID-1234456789";
    private static final String EMAIL = "nobody@nobody.com";

    private static final String FIRST_NAME = "Joe";
    private static final String LAST_NAME = "Bloggs";

    private static final String USER_NAME = "jbloggs";

    private static final Date EXPIRY_DATE = Calendar.getInstance().getTime();

    private static final String PASSWORD = "password";

    @Before
    public void setupReturnValuesofMockMethods() throws UserDetailsException {
        when(userDetailsRepositoryMock.findById(ID)).thenReturn(Optional.of(userDetailsMock));
        when(userDetailsRepositoryMock.findByEMail(EMAIL)).thenReturn(List.of(userDetailsMock));
        when(userDetailsRepositoryMock.findAll()).thenReturn(List.of(userDetailsMock));
        when(userDetailsMock.getEmail()).thenReturn(EMAIL);
        when(userDetailsMock.getId()).thenReturn(ID);
    }

    @Test
    public void findById() throws UserDetailsException {
        Optional<UserDetails> userDetails = service.findByID(ID);
        assertThat(userDetails, is(Optional.of(userDetailsMock)));
        assertThat(userDetails.get().getId(), is(ID));
    }

    @Test
    public void findByIdNotFound() {
        Exception exception = assertThrows(UserDetailsException.class, ()-> {
            service.findByID("");
        });

        String expectedMessage = "ID parameter is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findByEmail() throws UserDetailsException {
        List<UserDetails> results = service.findByEmail(EMAIL);
        assertThat(results, is(List.of(userDetailsMock)));
        assertThat(results.get(0).getEmail(), is(EMAIL));
    }

    @Test
    public void findByEmailNotFound() {
        Exception exception = assertThrows(UserDetailsException.class, ()-> {
            service.findByEmail("");
        });

        String expectedMessage = "Email parameter is empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAll()
    {
        assertThat(service.findAll().get(0), is(userDetailsMock));
    }

    @Test
    public void createUser() throws UserDetailsException {
        //prepare to capture a UserDetails Object
        ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);

        //invoke createNew
        UserDetails userDetails = new UserDetails(USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL, EXPIRY_DATE);
        userDetails.setId(ID);
        service.createUser(userDetails);
        //verify UserDetailsRepository.save invoked once and capture the UserDetails Object
        verify(userDetailsRepositoryMock).save(userDetailsCaptor.capture());

        //verify the attributes of the UserDetails Object
        assertThat(userDetailsCaptor.getValue().getId(), is(ID));
        assertThat(userDetailsCaptor.getValue().getEmail(), is(EMAIL));
        assertThat(userDetailsCaptor.getValue().getFirstName(), is(FIRST_NAME));
        assertThat(userDetailsCaptor.getValue().getLastName(), is(LAST_NAME));
        assertThat(userDetailsCaptor.getValue().getUserName(), is(USER_NAME));
        assertThat(userDetailsCaptor.getValue().getPassword(), is(PASSWORD));
        assertThat(userDetailsCaptor.getValue().getExpiryDate(), is(EXPIRY_DATE));
    }

    @Test
    public void creatUserInvalidData() throws UserDetailsException
    {
        Exception exception = assertThrows(UserDetailsException.class, ()-> {
            //invoke createNew wih no data
            service.createUser(new UserDetails());
        });

        String expectedMessage = "Email must be populated. User Not crated.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deleteUser() throws UserDetailsException {

        service.deleteUser(userDetailsMock);
        verify(userDetailsRepositoryMock).delete(userDetailsMock);
    }

    @Test
    public void deleteNotFound()
    {
        //when(userDetailsRepositoryMock.findById(ID)).thenReturn(List.of(userDetailsMock));
        when(userDetailsMock.getId()).thenReturn("");

        Assertions.assertThrows(UserDetailsException.class,
                () -> service.deleteUser(userDetailsMock));

    }

    @Test
    public void updateUser() throws UserDetailsException {
        //prepare to capture a UserDetails Object
        ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);

        //invoke createNew
        UserDetails userDetails = new UserDetails(USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL, EXPIRY_DATE);
        userDetails.setId(ID);
        service.updateUser(userDetails);
        //verify UserDetailsRepository.save invoked once and capture the UserDetails Object
        verify(userDetailsRepositoryMock).save(userDetailsCaptor.capture());

        //verify the attributes of the UserDetails Object
        assertThat(userDetailsCaptor.getValue().getId(), is(ID));
        assertThat(userDetailsCaptor.getValue().getEmail(), is(EMAIL));
        assertThat(userDetailsCaptor.getValue().getFirstName(), is(FIRST_NAME));
        assertThat(userDetailsCaptor.getValue().getLastName(), is(LAST_NAME));
        assertThat(userDetailsCaptor.getValue().getUserName(), is(USER_NAME));
        assertThat(userDetailsCaptor.getValue().getPassword(), is(PASSWORD));
        assertThat(userDetailsCaptor.getValue().getExpiryDate(), is(EXPIRY_DATE));
    }

    @Test
    public void updateUserInvalidData() throws UserDetailsException
    {
        //when(userDetailsMock.getId()).thenReturn("");
        Exception exception = assertThrows(UserDetailsException.class, ()-> {
            //invoke createNew wih no data
            service.updateUser(new UserDetails());
        });

        String expectedMessage = "User Record not found:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
