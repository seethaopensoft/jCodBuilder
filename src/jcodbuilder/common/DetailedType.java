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
 * The enum holds the mapping of all property types.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public enum DetailedType {

    PRIMITIVE_BYTE(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.BYTE),
    
    PRIMITIVE_SHORT(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.SHORT),
    
    PRIMITIVE_INT(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.INT),
    
    PRIMITIVE_LONG(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.LONG),
    
    PRIMITIVE_FLOAT(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.FLOAT),
    
    PRIMITIVE_DOUBLE(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.DOUBLE),
    
    PRIMITIVE_CHAR(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.CHAR),
    
    PRIMITIVE_BOOLEAN(PrimaryIdentifier.PRIMITIVE, SecondaryIdentifier.BOOLEAN),
    
    
    
    ENUM_TYPE(PrimaryIdentifier.ENUM, null),
    
    
    
    ARRAY_PRIMITIVE_BYTE(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.BYTE),
    
    ARRAY_PRIMITIVE_SHORT(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.SHORT),
    
    ARRAY_PRIMITIVE_INT(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.INT),
    
    ARRAY_PRIMITIVE_LONG(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.LONG),
    
    ARRAY_PRIMITIVE_FLOAT(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.FLOAT),
    
    ARRAY_PRIMITIVE_DOUBLE(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.DOUBLE),
    
    ARRAY_PRIMITIVE_CHAR(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.CHAR),
    
    ARRAY_PRIMITIVE_BOOLEAN(PrimaryIdentifier.ARRAY_PRIMITIVE, SecondaryIdentifier.BOOLEAN),
    
    ARRAY_OBJECT_TYPE(PrimaryIdentifier.ARRAY_OBJECT, SecondaryIdentifier.OBJECT),
    
    
    
    CUSTOM_OBJECT_TYPE(PrimaryIdentifier.CUSTOM_OBJECT, null),
    
    
    
    OBJECT_TYPE(PrimaryIdentifier.OBJECT, null),
    
    
    
    UNKNOWN(null, null);
    
    private PrimaryIdentifier primId;
    
    private SecondaryIdentifier secId;
    
    private DetailedType(PrimaryIdentifier primIdParam, SecondaryIdentifier secIdParam) {
        this.primId = primIdParam;
        this.secId = secIdParam;
    }

    public PrimaryIdentifier getPrimId() {
        return primId;
    }

    public void setPrimId(PrimaryIdentifier primId) {
        this.primId = primId;
    }

    public SecondaryIdentifier getSecId() {
        return secId;
    }

    public void setSecId(SecondaryIdentifier secId) {
        this.secId = secId;
    }
    
}
