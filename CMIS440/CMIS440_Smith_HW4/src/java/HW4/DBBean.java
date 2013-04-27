/**
 *   Document   : DBBean
 *   Created on : Apr 27, 2013, 2:46:19 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : Homework 4
 */


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package HW4;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Justin Smith
 */
public class DBBean implements Serializable {

    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";

    private String sampleProperty;

    private PropertyChangeSupport propertySupport;

    public DBBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
}
