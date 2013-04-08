/**
 *   Document   : Person
 *   Created on : Apr 7, 2013, 6:55:23 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Project 1
 */

package project1;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Justin Smith
 */
@ManagedBean
@SessionScoped
public class Person {
    
    private String salutation;
    private String fname;
    private String lname;
    private Integer age;
    private String email;
    
    public Person() {}
    
    public String register() { return "RegResult.jsp"; }
    

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}