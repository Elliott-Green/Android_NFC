package com.example.elliott.nfc2;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;

import java.sql.DriverManager;

/**
 * Created by Elliott on 15/05/2016.
 */
public class DB extends Activity
{
    private final String DB_URL = "jdbc:mysql://51.255.42.59:3306/NFC";
    private final String DB_USERNAME = "jroot";
    private final String DB_PASSWORD = "javapassword";

    public boolean setupUser(final String username, final String lastname, final String hashedKey, final int roleID)
    {
        try
        {
            Log.w("Logic - adding user", username);
            addUser(username, lastname, hashedKey, roleID);
            Thread.sleep(100);
            int newUserID = getNewUserID(hashedKey);
            Thread.sleep(100);
            addUsersPermission(roleID, newUserID);
            Log.w("Logic - user added", username);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return true;
    }


    public void addUser(final String username, final String lastname, final String hashedKey, final int roleID)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO USERS(_username, _password, _key) VALUES ('" + username + "','" + lastname + "','" + hashedKey + "');");

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public int getNewUserID(final String hashedKey)
    {
        int userID = -1;
        Log.w("InnerLog - userID - set", String.valueOf(userID) );
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT _userID FROM USERS WHERE _key = '" + hashedKey + "';");


            while(rs.next())
            {
                userID = Integer.parseInt(rs.getString("_userID"));
                ResultSetMetaData rsmd = rs.getMetaData();
                String test = rsmd.getColumnName(1);
                Log.w("TEST", test);
                Toast.makeText(getBaseContext(), String.valueOf(userID), Toast.LENGTH_LONG).show();
            }
            Log.w("InnerLog - RS", String.valueOf(rs.getString("_userID")));
            Log.w("InnerLog-userID - ?", String.valueOf(userID) );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return userID;
    }

    public void addUsersPermission(final int roleID, final int userID)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO USERSROLE (_roleID, _userID) VALUES ( '" + roleID + "','" + userID + "');");

            Log.w("Inner Logic - userID", String.valueOf(userID));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}


