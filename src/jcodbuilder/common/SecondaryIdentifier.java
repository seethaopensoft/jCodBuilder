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
package jcodbuilder.common;

/**
 * <pre>
 * <b>Description : </b>
 * The secondary classification of the property type.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public enum SecondaryIdentifier {

    BYTE(byte.class, "B"),
    
    SHORT(short.class, "S"),
    
    INT(int.class, "I"),
    
    LONG(long.class, "J"),
    
    FLOAT(float.class, "F"),
    
    DOUBLE(double.class, "D"),
    
    CHAR(char.class, "C"),
    
    BOOLEAN(boolean.class, "Z"),
    
    OBJECT(Object.class, "L");
    
    private Class primitiveClass;
    
    private String arrayElementType;
    
    private SecondaryIdentifier(Class primitiveClassParam, String arrayElementTypeParam) {
        this.primitiveClass = primitiveClassParam;
        this.arrayElementType = arrayElementTypeParam;
    }

    public Class getPrimitiveClass() {
        return primitiveClass;
    }

    public void setPrimitiveClass(Class primitiveClass) {
        this.primitiveClass = primitiveClass;
    }

    public String getArrayElementType() {
        return arrayElementType;
    }

    public void setArrayElementType(String arrayElementType) {
        this.arrayElementType = arrayElementType;
    }

}
