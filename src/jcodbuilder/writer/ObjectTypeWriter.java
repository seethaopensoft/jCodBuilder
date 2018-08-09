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

import static jcodbuilder.common.AppConstants.KEYWORD_MODIFIER_PRIVATE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_CLOSE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN_CLOSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jcodbuilder.dos.BaseObjectTypeInfoHolder;
import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.dos.SimpleTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.template.ObjectTemplate;

/**
 * <pre>
 * <b>Description : </b>
 * This class takes the parsed definition of object data and generates the source code for building it.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ObjectTypeWriter extends BasicWriter {

    private String modifierType = KEYWORD_MODIFIER_PRIVATE;
    
    private ObjectTypeInfoHolder infoHolder = null;
    
    private List<TypeInfoHolder> propertiesToBeProcessed = new ArrayList<TypeInfoHolder>();

    public ObjectTypeWriter(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        ObjectTypeInfoHolder infoHolderParam) {
        super(methodNameHelper, importedClassNames);
        infoHolder = infoHolderParam;
    }

    public void setModifierType(String modifierTypeParam) {
        this.modifierType = modifierTypeParam;
    }
    
    @Override
    public void process() {
        ObjectTemplate.writeHeaderSection(infoHolder, instructions, modifierType, importedClassNames);
        try {
            Map<String, TypeInfoHolder> properties = infoHolder.getProperties();
            if (properties == null || properties.isEmpty()) {
                return;
            }
            
            String[] propertyNameValuePair = new String[2];
            String valueOrAllocatedMethodName = null;
            for (Entry<String, TypeInfoHolder> property : properties.entrySet()) {
                propertyNameValuePair[0] = property.getKey();
                propertyNameValuePair[1] = null;
                valueOrAllocatedMethodName = TypeProcessor.preProcessTypeInfoHolder(methodNameHelper, 
                    importedClassNames, property.getValue(), propertyNameValuePair[0]);
                if (property.getValue() instanceof SimpleTypeInfoHolder) {
                    propertyNameValuePair[1] = valueOrAllocatedMethodName;
                } else if (property.getValue() instanceof BaseObjectTypeInfoHolder) {
                    BaseObjectTypeInfoHolder baseObjectTypeInfoHolder = (BaseObjectTypeInfoHolder) property.getValue();
                    baseObjectTypeInfoHolder.setAllocatedMethodName(valueOrAllocatedMethodName);
                    String allocatedMethodNameWithOrWithoutParam = null;
                    if (baseObjectTypeInfoHolder instanceof ObjectTypeInfoHolder 
                        && ((ObjectTypeInfoHolder) baseObjectTypeInfoHolder).getEnclosingClassName() != null) {
                        allocatedMethodNameWithOrWithoutParam = valueOrAllocatedMethodName + SYMBOL_PARENTHESIS_OPEN 
                            + baseObjectTypeInfoHolder.getLocalVariableName() + SYMBOL_PARENTHESIS_CLOSE;
                    } else {
                        allocatedMethodNameWithOrWithoutParam = valueOrAllocatedMethodName 
                            + SYMBOL_PARENTHESIS_OPEN_CLOSE;
                    }
                    
                    propertyNameValuePair[1] = allocatedMethodNameWithOrWithoutParam;
                    propertiesToBeProcessed.add(baseObjectTypeInfoHolder);
                }
                ObjectTemplate.writeBodySection(infoHolder, instructions, propertyNameValuePair);
            }
        } finally {
            ObjectTemplate.writeFooterSection(infoHolder, instructions);
        }
        if (!propertiesToBeProcessed.isEmpty()) {
            String instructions;
            for (TypeInfoHolder property : propertiesToBeProcessed) {
                instructions = TypeProcessor.processTypeInfoHolder(methodNameHelper, importedClassNames, property);
                addToCurrentInstruction(instructions);
            }
        }
    }
    
}
