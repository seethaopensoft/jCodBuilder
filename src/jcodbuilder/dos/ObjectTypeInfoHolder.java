/*
 * Copyright 2018 Seetharaman Radhakrishnan
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 * 
 */
package jcodbuilder.dos;

import static jcodbuilder.common.AppConstants.*;

import java.util.Map;

/**
 * <pre>
 * <b>Description : </b>
 * This class holds the information about the property of reference type.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ObjectTypeInfoHolder extends BaseObjectTypeInfoHolder {
    
    private String enclosingClassName;

    private Map<String, TypeInfoHolder> properties;
    
    public ObjectTypeInfoHolder() {
        super(STR_LOCAL_VARIABLE_NAME_FOR_OBJECT);
    }

    public String getEnclosingClassName() {
        return enclosingClassName;
    }

    public void setEnclosingClassName(String enclosingClassName) {
        this.enclosingClassName = enclosingClassName;
    }

    public Map<String, TypeInfoHolder> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, TypeInfoHolder> properties) {
        this.properties = properties;
    }
    
}
