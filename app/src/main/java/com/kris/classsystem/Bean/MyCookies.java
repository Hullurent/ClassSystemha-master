package com.kris.classsystem.Bean;

import java.io.Serializable;

/**
 * Java Beans
 **/

public class MyCookies implements Serializable {

    // Useful Cookies
    private String JSESSIONID;
    private String jwxkxt;
    private String ASP_sessionid;
    private String ticket;


    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJwxkxt(String jwxkxt) {
        this.jwxkxt = jwxkxt;
    }

    public String getJwxkxt() {
        return jwxkxt;
    }

    public void setASP_sessionid(String ASP_sessionid) {
        this.ASP_sessionid = ASP_sessionid;
    }

    public String getASP_sessionid() {
        return ASP_sessionid;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }

    public MyCookies(String JSESSIONID, String jwxkxt, String ASP_sessionid, String ticket) {
        this.setJSESSIONID(JSESSIONID);
        this.setJwxkxt(jwxkxt);
        this.setASP_sessionid(ASP_sessionid);
        this.setTicket(ticket);
    }

}
