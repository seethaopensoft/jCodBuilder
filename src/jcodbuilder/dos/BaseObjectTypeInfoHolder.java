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

/**
 * <pre>
 * <b>Description : </b>
 * This class holds the information about the property of object type. This serves as a base for many object based types
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class BaseObjectTypeInfoHolder extends TypeInfoHolder {

    private String allocatedMethodName = null;
    
    private String localVariableName;
    
    public BaseObjectTypeInfoHolder(String localVariableName) {
        super();
        this.localVariableName = localVariableName;
    }

    public String getAllocatedMethodName() {
        return allocatedMethodName;
    }

    public void setAllocatedMethodName(String allocatedMethodName) {
        this.allocatedMethodName = allocatedMethodName;
    }
    
    public String getLocalVariableName() {
        return localVariableName;
    }

    public void setLocalVariableName(String localVariableNameParam) {
        this.localVariableName = localVariableNameParam;
    }

}
