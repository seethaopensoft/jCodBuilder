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
package jcodbuilder.handler;

import static jcodbuilder.common.AppConstants.STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE1;
import static jcodbuilder.common.AppConstants.STR_LOCAL_VARIABLE_NAME_FOR_COLLECTION_OBJECT;
import static jcodbuilder.common.AppConstants.STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.helper.MethodNameHelper.MethodNameType;
import jcodbuilder.util.ParserUtil;
import jcodbuilder.writer.Type1CollectionWriter;

/**
 * <pre>
 * <b>Description : </b>
 * This covers all objects of type "Collection" interface.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type1CollectionHandler implements CustomObjectTypeHandler {

    @Override
    public CustomObjectTypeInfoHolder handleExtraction(Class dataType, Object object, 
        ImportStatementHelper importStmtHelper, ObjectParser callbackParser) {
        CustomObjectTypeInfoHolder infoHolder = null;
        if (object != null) {
            infoHolder = ParserUtil.createCustomObjectTypeInfoHolder(dataType, importStmtHelper, this,
                STR_LOCAL_VARIABLE_NAME_FOR_COLLECTION_OBJECT);
            Collection tempType1Collection = (Collection) object;
            List<TypeInfoHolder> type1CollectionData = new ArrayList<TypeInfoHolder>();
            for (Object elementValue : tempType1Collection) {
                if (elementValue != null) {
                    type1CollectionData.add(callbackParser.process(elementValue.getClass(), elementValue));
                } else {
                    type1CollectionData.add(null);   
                }
            }
            if (!type1CollectionData.isEmpty()) {
                infoHolder.setCustomDataHolder(type1CollectionData);
            }
        }
        return infoHolder;
    }

    @Override
    public String handleGenerationPreProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam, String propertyName) {
        if (STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM.equals(propertyName)) {
            propertyName = STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE1;
        } else {
            if (propertyName != null) {
                propertyName = propertyName.substring(3);
            }
        }
        return methodNameHelper.getMethodName(MethodNameType.METHOD_NAME_FOR_COLLECTION, propertyName);
    }

    @Override
    public String handleGenerationProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam) {
        Type1CollectionWriter writer = new Type1CollectionWriter(methodNameHelper, importedClassNames, infoHolderParam);
        writer.process();
        return writer.getInstruction();
    }

}
