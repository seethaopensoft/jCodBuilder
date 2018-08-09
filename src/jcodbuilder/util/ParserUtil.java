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
package jcodbuilder.util;

import static jcodbuilder.common.AppConstants.SLIZING_SIZE_FOR_STRING_SPLIT_INTO_MULTI_LINE;
import static jcodbuilder.common.AppConstants.SYMBOL_DOUBLE_QUOTE;
import static jcodbuilder.common.AppConstants.SYMBOL_SINGLE_QUOTE;

import java.lang.reflect.Modifier;

import jcodbuilder.codegen.TypeClassifier;
import jcodbuilder.common.DetailedType;
import jcodbuilder.common.PrimaryIdentifier;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.dos.SimpleTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.handler.CustomObjectHandlerMapper;
import jcodbuilder.handler.CustomObjectTypeHandler;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.template.TemplateUtil;

/**
 * <pre>
 * <b>Description : </b>
 * Util class contains functions used by <code>Parser</code>.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ParserUtil {

    public static void populateQualifierAndClassName(TypeInfoHolder infoHolder, Class objectType, 
        ImportStatementHelper importStmtHelper) {
        String[] qualifierAndClassName = ImportStatementUtil.extractQualifierAndClassName(objectType);
        infoHolder.setQualifier(qualifierAndClassName[0]);
        infoHolder.setClassName(qualifierAndClassName[1]);
        importStmtHelper.addQualifiedClassName(qualifierAndClassName[0], qualifierAndClassName[1]);
    }
    
    public static SimpleTypeInfoHolder createSimpleTypeInfoHolder(Class objectType, String value, 
        ImportStatementHelper importStmtHelper) {
        SimpleTypeInfoHolder infoHolder = new SimpleTypeInfoHolder();
        if (objectType != null) {
            populateQualifierAndClassName(infoHolder, objectType, importStmtHelper);
        }
        infoHolder.setValue(value);
        return infoHolder;
    }
    
    public static ObjectTypeInfoHolder createObjectTypeInfoHolder(Class objectType, 
        ImportStatementHelper importStmtHelper) {
        ObjectTypeInfoHolder infoHolder = new ObjectTypeInfoHolder();
        populateQualifierAndClassName(infoHolder, objectType, importStmtHelper);
        if (isInnerClass(objectType)) {
            infoHolder.setEnclosingClassName(objectType.getEnclosingClass().getSimpleName());
        }
        return infoHolder;
    }
    
    public static CustomObjectTypeInfoHolder createCustomObjectTypeInfoHolder(Class objectType, 
        ImportStatementHelper importStmtHelper, CustomObjectTypeHandler handlerReference, String localVariableName) {
        CustomObjectTypeInfoHolder infoHolder = new CustomObjectTypeInfoHolder();
        populateQualifierAndClassName(infoHolder, objectType, importStmtHelper);
        if (isInnerClass(objectType)) {
            infoHolder.setEnclosingClassName(objectType.getEnclosingClass().getSimpleName());
        }
        infoHolder.setHandlerReference(handlerReference);
        infoHolder.setLocalVariableName(localVariableName);
        return infoHolder;
    }
        
    
    public static boolean isInnerClass(Class objectType) {
        return (objectType.isMemberClass() && !Modifier.isStatic(objectType.getModifiers()));
    }
    
    public static String wrapDoubleQuoteForValue(String value) {
        return SYMBOL_DOUBLE_QUOTE + value + SYMBOL_DOUBLE_QUOTE;
    }
    
    public static String wrapDoubleQuoteForString(String value) {
        return wrapDoubleQuoteForValue(unescapeStringContent(value));
    }

    public static String wrapSingleQuoteForChar(String value) {
        return SYMBOL_SINGLE_QUOTE + value + SYMBOL_SINGLE_QUOTE;
    }
    
    public static String unescapeStringContent(String value) {
        String[] unescapeChars = {"\\", "\b", "\t", "\n", "\f", "\r", "\""};
        String[] unescapeReplacementChars = {"\\\\", "\\b", "\\t", "\\n", "\\f", "\\r", "\\\""};
        
        for (int index = 0; index < unescapeChars.length; index++) {
            value = value.replace(unescapeChars[index], unescapeReplacementChars[index]);
        }
        return value;
    }
    
    public static String splitStringDataIntoMultiLinesString(String data, int indentLevel) {
        return splitStringDataIntoMultiLinesString(data, SLIZING_SIZE_FOR_STRING_SPLIT_INTO_MULTI_LINE, indentLevel);
    }
    
    public static String splitStringDataIntoMultiLinesString(String data, final int slicingSize, int indentLevel) {
        if (data == null) {
            return null;
        }
        if (data.length() == 0 || data.length() <= slicingSize) {
            return wrapDoubleQuoteForString(data);
        }
        StringBuilder sb_ip = new StringBuilder(data);
        StringBuilder sb_op = new StringBuilder();
        int length = sb_ip.length();
        int start = 0;
        int end = 0;
        boolean firstRun = true;
        
        do {
            if ((length - start) > slicingSize) {
                end += slicingSize;
            } else {
                end = length;
            }
            if (firstRun) {
                firstRun = false;
            } else {
                sb_op.append("\n" + TemplateUtil.getIndentFor(indentLevel) + " + ");
            }
            sb_op.append(wrapDoubleQuoteForString(sb_ip.substring(start, end)));
            start = end;
        } while (start < length);
        return sb_op.toString();
    }
    
    public static boolean isValidClassType(Class objectType) {
        if (!objectType.isLocalClass() && Modifier.isPublic(objectType.getModifiers())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAnonymousClassAssignedToConcreteClassType(Class runtimePropertyType, 
        Class declaredPropertyType) {
        return (runtimePropertyType.isAnonymousClass() && !declaredPropertyType.isInterface() 
            && !Modifier.isAbstract(declaredPropertyType.getModifiers()));
    }
    
    public static boolean validateIfInnerClass(Class enclosingClassType, Class propertyClassType) {
        if (ParserUtil.isInnerClass(propertyClassType)) {
            if (propertyClassType.getEnclosingClass() == enclosingClassType) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isPojoObject(Class objectType) {
        return CustomObjectHandlerMapper.checkAndRetrieveCustomObjectTypeHandler(objectType) == null;
    }

    public static boolean isValidTypeForProcessing(Class objectType) {
        DetailedType detailedType = TypeClassifier.classifyType(objectType);
        return (detailedType != null && PrimaryIdentifier.OBJECT.equals(detailedType.getPrimId()) 
            && isValidClassType(objectType) 
            && !objectType.isAnonymousClass()
            && !isInnerClass(objectType)
            && CustomObjectHandlerMapper.checkAndRetrieveCustomObjectTypeHandler(objectType) == null);
    }
}
