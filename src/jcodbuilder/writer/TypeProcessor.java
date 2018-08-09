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

import static jcodbuilder.common.AppConstants.KEYWORD_NEW_WITH_SPACE;
import static jcodbuilder.common.AppConstants.STR_GENERIC_METHOD_NAME_FOR_SIMPLE_TYPE;
import static jcodbuilder.common.AppConstants.STR_UNKNOWN;
import static jcodbuilder.common.AppConstants.STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM;
import static jcodbuilder.common.AppConstants.SYMBOL_DOT;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_CLOSE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN;

import java.util.Set;

import jcodbuilder.dos.ArrayInfoHolder;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.dos.SimpleTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.dos.ArrayInfoHolder.ArrayType;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.helper.MethodNameHelper.MethodNameType;
import jcodbuilder.logger.AppLoggerUtil;
import jcodbuilder.util.ImportStatementUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This class is responsible for identifying and returning the <code>Writer</code> object for handling the supplied 
 * parsed definition of data.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class TypeProcessor {
    
    public static String preProcessTypeInfoHolder(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        TypeInfoHolder infoHolder, String propertyName) {

        if (infoHolder instanceof SimpleTypeInfoHolder) {
            SimpleTypeInfoHolder simpleTypeInfoHolder = (SimpleTypeInfoHolder) infoHolder;
            if (simpleTypeInfoHolder.getClassName() != null) {
                String qualifiedClassName = ImportStatementUtil.getQualifiedClassName(
                    simpleTypeInfoHolder.getQualifier(), simpleTypeInfoHolder.getClassName(), importedClassNames);
                if (simpleTypeInfoHolder.isEnumType()) {
                    return qualifiedClassName + SYMBOL_DOT + simpleTypeInfoHolder.getValue();
                } else {
                    return KEYWORD_NEW_WITH_SPACE + qualifiedClassName + SYMBOL_PARENTHESIS_OPEN 
                        + simpleTypeInfoHolder.getValue() + SYMBOL_PARENTHESIS_CLOSE;
                }
            } else {
                return simpleTypeInfoHolder.getValue();
            }
        } else if (infoHolder instanceof ObjectTypeInfoHolder) {
            return methodNameHelper.getMethodName(MethodNameType.METHOD_NAME_FOR_OBJECT, infoHolder.getClassName());
        } else if (infoHolder instanceof CustomObjectTypeInfoHolder) {
            CustomObjectTypeInfoHolder customObjInfoHolder = (CustomObjectTypeInfoHolder) infoHolder;
            if (customObjInfoHolder.getHandlerReference() != null) {
                return customObjInfoHolder.getHandlerReference().handleGenerationPreProcess(methodNameHelper, 
                    importedClassNames, customObjInfoHolder, propertyName);
            } else {
                AppLoggerUtil.println("HandlerReference was not set on CustomObjectTypeInfoHolder by "
                    + "CustomObjectTypeHandler implementer");
                return STR_UNKNOWN;
            }
        } else if (infoHolder instanceof ArrayInfoHolder) {
            ArrayInfoHolder arrayInfoHolder = (ArrayInfoHolder) infoHolder;
            String methodNameFor = null;
            if (STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM.equals(propertyName)) {
                if (ArrayType.SIMPLE_TYPE_ARRAY.equals(arrayInfoHolder.getArrayType())) {
                    methodNameFor = STR_GENERIC_METHOD_NAME_FOR_SIMPLE_TYPE;
                } else {
                    methodNameFor = arrayInfoHolder.getClassName();
                }
            } else {
                if (propertyName != null) {
                    methodNameFor = propertyName.substring(3);
                }
            }
            return methodNameHelper.getMethodName(MethodNameType.METHOD_NAME_FOR_ARRAY, methodNameFor);
        }
        return null;
    }
    
    public static String processTypeInfoHolder(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        TypeInfoHolder infoHolder) {
        Writer writer = createWriterForTypeInfoHolder(methodNameHelper, importedClassNames, infoHolder);
        if (writer != null) {
            writer.process();
            return writer.getInstruction();
        }
        return null;
    }
 
    private static Writer createWriterForTypeInfoHolder(MethodNameHelper methodNameHelper, 
        Set<String> importedClassNames, TypeInfoHolder infoHolder) {
        
        Writer writer = null;
        if (infoHolder instanceof ObjectTypeInfoHolder) {
            writer = new ObjectTypeWriter(methodNameHelper, importedClassNames, (ObjectTypeInfoHolder) infoHolder);
        } else if (infoHolder instanceof ArrayInfoHolder) {
            writer = new ArrayTypeWriter(methodNameHelper, importedClassNames, (ArrayInfoHolder) infoHolder);
        } else if (infoHolder instanceof CustomObjectTypeInfoHolder) {
            writer = new CustomObjectTypeWriter(methodNameHelper, importedClassNames, 
                (CustomObjectTypeInfoHolder ) infoHolder);
        }
        return writer;
    }
    
}
