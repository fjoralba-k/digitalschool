package org.zerogravitysolutions.digitalschool.exceptions;

public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message) {
        super(message);
    }
}
