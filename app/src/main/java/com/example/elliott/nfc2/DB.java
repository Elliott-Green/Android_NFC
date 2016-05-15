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
public class DB extends Activity {
    public void addUser(final String username, final String lastname, final String hashedKey, final int roleID) {
        try
        {
            int userID = 0;

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://51.255.42.59:3306/NFC", "jroot", "javapassword");

            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO USERS (_username, _password, _key) VALUES ('"+ username + "','" + lastname + "','" + hashedKey +"');");

            Statement st1 = con.createStatement();
            ResultSet rs = st1.executeQuery("SELECT _userID FROM USERS WHERE _key LIKE '" + hashedKey + "');");
            while (rs.next())
            {
                  userID = rs.getInt("_userID");
            }


            Statement st2 = con.createStatement();
            st2.executeUpdate("INSERT INTO USERSROLE (_roleID, _userID) VALUES ( '" + roleID + "','" + userID + "');");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}


