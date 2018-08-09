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

import java.util.List;

/**
 * <pre>
 * <b>Description : </b>
 * This class holds the information about the property of array type.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ArrayInfoHolder extends BaseObjectTypeInfoHolder {
    
    private ArrayType arrayType;
    
    private int arraySize;
    
    private int arrayDimensions;
    
    private List<TypeInfoHolder> arrayData;
    
    private List<Integer> arrayDataIndices;
    
    private boolean joinMultiLinesIntoSingleLine;
    
    public ArrayInfoHolder() {
        super(STR_LOCAL_VARIABLE_NAME_FOR_ARRAY_OBJECT);
    }

    public ArrayType getArrayType() {
        return arrayType;
    }

    public void setArrayType(ArrayType arrayType) {
        this.arrayType = arrayType;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public int getArrayDimensions() {
        return arrayDimensions;
    }

    public void setArrayDimensions(int arrayDimensions) {
        this.arrayDimensions = arrayDimensions;
    }

    public List<TypeInfoHolder> getArrayData() {
        return arrayData;
    }

    public void setArrayData(List<TypeInfoHolder> arrayData) {
        this.arrayData = arrayData;
    }

    public List<Integer> getArrayDataIndices() {
        return arrayDataIndices;
    }

    public void setArrayDataIndices(List<Integer> arrayDataIndices) {
        this.arrayDataIndices = arrayDataIndices;
    }

    public boolean isJoinMultiLinesIntoSingleLine() {
        return joinMultiLinesIntoSingleLine;
    }

    public void setJoinMultiLinesIntoSingleLine(boolean joinMultiLinesIntoSingleLine) {
        this.joinMultiLinesIntoSingleLine = joinMultiLinesIntoSingleLine;
    }

    public enum ArrayType {
        
        SIMPLE_TYPE_ARRAY,
        
        OBJECT_TYPE_ARRAY;
    }
    
}
