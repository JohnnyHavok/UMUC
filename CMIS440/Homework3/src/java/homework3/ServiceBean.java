/**
 *   Document   : ServiceBean
 *   Created on : Apr 14, 2013, 2:01:21 PM
 *   Author     : Justin Smith
 *   Course     : CMIS 440
 *   Project    : 
 */

package homework3;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Justin Smith
 */
public class ServiceBean implements Serializable {

    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";

    private String sampleProperty;

    private PropertyChangeSupport propertySupport;

    public ServiceBean() {
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


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
