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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jcodbuilder.dos.BaseObjectTypeInfoHolder;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.template.Type2CollectionTemplate;

/**
 * <pre>
 * <b>Description : </b>
 * This class takes the parsed definition of collection data of type <code>Map</code> and generates the source code 
 * for building it.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type2CollectionWriter extends BasicWriter {

    private CustomObjectTypeInfoHolder infoHolder = null;
    
    private List<TypeInfoHolder> collectionElementsToBeProcessed = new ArrayList<TypeInfoHolder>();
    
    public Type2CollectionWriter(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        CustomObjectTypeInfoHolder infoHolderParam) {
        super(methodNameHelper, importedClassNames);
        infoHolder = infoHolderParam;
    }

    @Override
    public void process() {
        Type2CollectionTemplate.writeHeaderSection(infoHolder, instructions, importedClassNames);
        try {
            Map<TypeInfoHolder, TypeInfoHolder> collectionData = (Map<TypeInfoHolder, TypeInfoHolder>) 
                infoHolder.getCustomDataHolder();
            if (collectionData == null || collectionData.isEmpty()) {
                return;
            }

            String valueOrAllocatedMethodNameForKey;
            String valueOrAllocatedMethodNameForValue;
            Set<Entry<TypeInfoHolder, TypeInfoHolder>> entries = collectionData.entrySet();
            for (Entry<TypeInfoHolder, TypeInfoHolder> entry : entries) {
                valueOrAllocatedMethodNameForKey = preProcessItem(entry.getKey());
                valueOrAllocatedMethodNameForValue = preProcessItem(entry.getValue());
                Type2CollectionTemplate.writeBodySection(infoHolder, instructions, valueOrAllocatedMethodNameForKey, 
                    valueOrAllocatedMethodNameForValue);
            }
        } finally {
            Type2CollectionTemplate.writeFooterSection(infoHolder, instructions);
        }
        if (!collectionElementsToBeProcessed.isEmpty()) {
            String instructions;
            for (TypeInfoHolder property : collectionElementsToBeProcessed) {
                instructions = TypeProcessor.processTypeInfoHolder(methodNameHelper, importedClassNames, property);
                addToCurrentInstruction(instructions);
            }
        }
    }
    
    private String preProcessItem(TypeInfoHolder infoHolderParam) {
        String valueOrAllocatedMethodName = TypeProcessor.preProcessTypeInfoHolder(methodNameHelper, importedClassNames,
            infoHolderParam, STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM);
        if (infoHolderParam instanceof BaseObjectTypeInfoHolder) {
            BaseObjectTypeInfoHolder temp = (BaseObjectTypeInfoHolder) infoHolderParam;
            temp.setAllocatedMethodName(valueOrAllocatedMethodName);
            collectionElementsToBeProcessed.add(infoHolderParam);
            valueOrAllocatedMethodName = valueOrAllocatedMethodName + SYMBOL_PARENTHESIS_OPEN_CLOSE;
        }
        return valueOrAllocatedMethodName;
    }
    
}
