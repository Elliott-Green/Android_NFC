package com.example.elliott.nfc2;

/**
 * Created by Elliott on 10/05/2016.
 */
public class User
{
    private final int _userID;
    private final String _username;
    private final String _password;
    private final String  _key;


    public User(int userID, String username, String password, String key)
    {
        _userID = userID;
        _username = username;
        _password = password;
        _key = key;

    }

    public String get_key() {
        return _key;
    }

    public String get_password() {
        return _password;
    }

    public int get_userID() {
        return _userID;
    }

    public String get_username() {
        return _username;
    }
}
