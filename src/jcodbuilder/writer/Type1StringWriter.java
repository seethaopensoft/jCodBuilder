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

import java.util.Set;

import jcodbuilder.common.AppConstants;
import jcodbuilder.dos.ArrayInfoHolder;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.template.Type1StringTemplate;

/**
 * <pre>
 * <b>Description : </b>
 * This class takes the parsed definition of string data and generates the source code for building it.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type1StringWriter extends BasicWriter {

    private CustomObjectTypeInfoHolder infoHolder = null;

    public Type1StringWriter(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        CustomObjectTypeInfoHolder infoHolderParam) {
        super(methodNameHelper, importedClassNames);
        infoHolder = infoHolderParam;
    }

    @Override
    public void process() {
        ArrayInfoHolder arrayInfoHolder = (ArrayInfoHolder) infoHolder.getCustomDataHolder();
        String charArrayMethodName = null;
        if (arrayInfoHolder != null) {
            charArrayMethodName = TypeProcessor.preProcessTypeInfoHolder(methodNameHelper, importedClassNames, 
                arrayInfoHolder, STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM);
            arrayInfoHolder.setAllocatedMethodName(charArrayMethodName);
        }
        Type1StringTemplate.writeHeaderSection(infoHolder, instructions, AppConstants.KEYWORD_MODIFIER_PRIVATE, 
            importedClassNames, charArrayMethodName);
        Type1StringTemplate.writeFooterSection(infoHolder, instructions);
        if (arrayInfoHolder != null) {
            String instructions = TypeProcessor.processTypeInfoHolder(methodNameHelper, importedClassNames, 
                arrayInfoHolder);
            addToCurrentInstruction(instructions);
        }
    }

}
