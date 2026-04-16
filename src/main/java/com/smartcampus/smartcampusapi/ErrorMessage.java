package com.smartcampus.smartcampusapi;

/**
 * Adapted from Tutorial Week 09 (ErrorMessage.java)
 * Provides a standardised JSON error response structure for all API errors.
 * Ensures clients always receive a JSON error payload rather than a raw Java
 * stack trace or plain text message.
 */

public class ErrorMessage {

    // The human-readable error description sent to the client
    private String errorMessage;
    // The HTTP status code associated with this error
    private int errorCode;
    // Link to API error documentation for developer reference
    private String documentation;

    // Default constructor required by Jackson for JSON deserialisation
    public ErrorMessage() {}

    // Convenience constructor for building a complete error response
    public ErrorMessage(String errorMessage, int errorCode, String documentation) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentation = documentation;
    }

    // Getters and Setters
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }

    public String getDocumentation() { return documentation; }
    public void setDocumentation(String documentation) { this.documentation = documentation; }
}