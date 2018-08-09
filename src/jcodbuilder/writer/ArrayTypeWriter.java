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
package jcodbuilder.writer;

import static jcodbuilder.common.AppConstants.STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN_CLOSE;
import static jcodbuilder.common.PropertiesBasedAppConstants.COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jcodbuilder.dos.ArrayInfoHolder;
import jcodbuilder.dos.BaseObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.template.ArrayTemplate;

/**
 * <pre>
 * <b>Description : </b>
 * This class takes the parsed definition of array data and generates the source code for building it.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ArrayTypeWriter extends BasicWriter {

    private ArrayInfoHolder infoHolder = null;
    
    private List<TypeInfoHolder> arrayDataToBeProcessed = new ArrayList<TypeInfoHolder>();
    
    public ArrayTypeWriter(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        ArrayInfoHolder infoHolderParam) {
        super(methodNameHelper, importedClassNames);
        infoHolder = infoHolderParam;
    }

    @Override
    public void process() {
        ArrayTemplate.writeHeaderSection(infoHolder, instructions, importedClassNames);
        try {
            List<TypeInfoHolder> arrayData = infoHolder.getArrayData();
            List<Integer> arrayDataIndices = infoHolder.getArrayDataIndices();
            if (arrayData == null || arrayData.isEmpty() || arrayDataIndices == null || arrayDataIndices.isEmpty() 
                || arrayData.size() != arrayDataIndices.size()) {
                return;
            }
                
            TypeInfoHolder tempInfoHolder;
            String valueOrAllocatedMethodName;
            int arraySize = arrayData.size();
            int count = 0;
            List<String> indices = new ArrayList<String>();
            List<String> values = new ArrayList<String>();
            String indexData = null;
            String valueData = null;
            
            for (int index = 0; index < arraySize; index++) {
                tempInfoHolder = arrayData.get(index);
                valueOrAllocatedMethodName = TypeProcessor.preProcessTypeInfoHolder(methodNameHelper, 
                    importedClassNames, tempInfoHolder, STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM);
                if (tempInfoHolder instanceof BaseObjectTypeInfoHolder) {
                    BaseObjectTypeInfoHolder temp = (BaseObjectTypeInfoHolder) tempInfoHolder;
                    temp.setAllocatedMethodName(valueOrAllocatedMethodName);
                    valueOrAllocatedMethodName = valueOrAllocatedMethodName + SYMBOL_PARENTHESIS_OPEN_CLOSE;
                    arrayDataToBeProcessed.add(tempInfoHolder);
                }
                indexData = String.valueOf(arrayDataIndices.get(index));
                valueData = valueOrAllocatedMethodName;
                if (!infoHolder.isJoinMultiLinesIntoSingleLine()) {
                    ArrayTemplate.writeBodySection(infoHolder, instructions, indexData, valueData);
                } else {
                    indices.add(indexData);
                    values.add(valueData);
                    count++;
                    if (index == (arraySize - 1) || count == COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR) {
                        ArrayTemplate.writeBodySection(infoHolder, instructions, indices, values);
                        indices.clear();
                        values.clear();
                        count = 0;
                    }
                }
            }
        } finally {
            ArrayTemplate.writeFooterSection(infoHolder, instructions);
        }
        if (!arrayDataToBeProcessed.isEmpty()) {
            String instructions;
            for (TypeInfoHolder property : arrayDataToBeProcessed) {
                instructions = TypeProcessor.processTypeInfoHolder(methodNameHelper, importedClassNames, property);
                addToCurrentInstruction(instructions);
            }
        }
    }
    
}
