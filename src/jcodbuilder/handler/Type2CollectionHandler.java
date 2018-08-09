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

import static jcodbuilder.common.AppConstants.STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE2;
import static jcodbuilder.common.AppConstants.STR_LOCAL_VARIABLE_NAME_FOR_COLLECTION_OBJECT;
import static jcodbuilder.common.AppConstants.STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.helper.MethodNameHelper.MethodNameType;
import jcodbuilder.util.ParserUtil;
import jcodbuilder.writer.Type2CollectionWriter;

/**
 * <pre>
 * <b>Description : </b>
 * This covers all objects of type "Map" interface.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type2CollectionHandler implements CustomObjectTypeHandler {

    @Override
    public CustomObjectTypeInfoHolder handleExtraction(Class dataType, Object object, 
        ImportStatementHelper importStmtHelper, ObjectParser callbackParser) {
        CustomObjectTypeInfoHolder infoHolder = null;
        if (object != null) {
            infoHolder = ParserUtil.createCustomObjectTypeInfoHolder(dataType, importStmtHelper, this,
                STR_LOCAL_VARIABLE_NAME_FOR_COLLECTION_OBJECT);
            Map tempType2Collection = (Map) object;
            Map<TypeInfoHolder, TypeInfoHolder> type2CollectionData = 
                new LinkedHashMap<TypeInfoHolder, TypeInfoHolder>();
            TypeInfoHolder infoHolderEntryKey = null;
            TypeInfoHolder infoHolderEntryValue = null;
            Object entryKey = null;
            Object entryValue = null;
            
            Set<Entry<Object, Object>> collectionEntries = tempType2Collection.entrySet();
            for (Entry<Object, Object> collectionEntry : collectionEntries) {
                entryKey = collectionEntry.getKey();
                entryValue = collectionEntry.getValue();
                if (entryKey != null) {
                    infoHolderEntryKey = callbackParser.process(entryKey.getClass(), entryKey);
                } else {
                    infoHolderEntryKey = null;
                }
                if (entryValue != null) {
                    infoHolderEntryValue = callbackParser.process(entryValue.getClass(), entryValue);
                } else {
                    infoHolderEntryValue = null;
                }
                type2CollectionData.put(infoHolderEntryKey, infoHolderEntryValue);
            }
            if (!type2CollectionData.isEmpty()) {
                infoHolder.setCustomDataHolder(type2CollectionData);
            }
        }
        return infoHolder;
    }

    @Override
    public String handleGenerationPreProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam, String propertyName) {
        if (STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM.equals(propertyName)) {
            propertyName = STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE2;
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
        Type2CollectionWriter writer = new Type2CollectionWriter(methodNameHelper, importedClassNames, infoHolderParam);
        writer.process();
        return writer.getInstruction();
    }
    
}
