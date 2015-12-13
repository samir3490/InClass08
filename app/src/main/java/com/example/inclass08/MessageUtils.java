/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import java.util.Date;

/**
 * Created by Samir on 11/03/15.
 */
public class MessageUtils {
    String username, message;
Date time;
    public MessageUtils() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MessageUtils{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
