package org.weewelchie.security.authentication.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

public class UserDetailsId implements Serializable {

    private String id;

    private String username;

    public UserDetailsId()
    {
        this(null,null);
    }

    public UserDetailsId(String id, String userName)
    {
        this.id = id;
        this.username = userName;
    }
    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBRangeKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
