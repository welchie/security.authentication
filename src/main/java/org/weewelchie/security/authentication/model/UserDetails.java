package org.weewelchie.security.authentication.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Objects;

@DynamoDbBean
@DynamoDBTable(tableName = "UserDetails")
public class UserDetails {

    @Id
    private UserDetailsId userDetailsId;

    public UserDetails() {}

    public UserDetails(String userName, String firstName, String lastName, String password, String email, String expiryDate)
    {
        this.username = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    private String id;
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String expiryDate;

    private String lastUpdatedDate;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
   //@DynamoDBRangeKey(attributeName = "username")
    //@DynamoDBRangeKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    @DynamoDBAttribute(attributeName = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @DynamoDBAttribute(attributeName = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @DynamoDBAttribute(attributeName = "expiry_date")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }



    @DynamoDBAttribute(attributeName = "last_updated_date")
    @DynamoDBAutoGeneratedTimestamp(strategy= DynamoDBAutoGenerateStrategy.ALWAYS)
    public String getLastUpdatedDate() { return lastUpdatedDate; }
    public void setLastUpdatedDate(String lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, password, expiryDate, lastUpdatedDate);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id='" + id + '\'' +
                ", userName='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='********************'" +
                ", expiryDate=" + expiryDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}