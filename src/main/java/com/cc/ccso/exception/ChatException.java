package com.cc.ccso.exception;

/**
 * Custom exception class for chat-related errors
 *
 * @author plexpt
 */
public class ChatException extends RuntimeException {


    public ChatException(String msg) {
        super(msg);
    }


}