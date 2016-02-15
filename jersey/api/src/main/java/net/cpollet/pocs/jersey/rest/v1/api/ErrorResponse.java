package net.cpollet.pocs.jersey.rest.v1.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Christophe Pollet
 */
@XmlRootElement
public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse() {
        // nothing
    }

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
