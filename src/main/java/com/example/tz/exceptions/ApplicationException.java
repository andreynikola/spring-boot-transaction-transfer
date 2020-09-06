package com.example.tz.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class ApplicationException extends Exception {

    /**
     * @param message
     */
    public ApplicationException(String message) {
        super(message);
    }

}
