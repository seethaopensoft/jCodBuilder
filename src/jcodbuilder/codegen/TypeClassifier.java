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
package jcodbuilder.codegen;

import static jcodbuilder.common.AppConstants.SYMBOL_SEMI_COLON;
import static jcodbuilder.common.AppConstants.SYMBOL_SQUARE_BRACKET_OPEN;
import static jcodbuilder.common.AppConstants.TXT_NO_SPACE;
import jcodbuilder.common.DetailedType;
import jcodbuilder.common.SecondaryIdentifier;

/**
 * <pre>
 * <b>Description : </b>
 * This class is used to identify the property type.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class TypeClassifier {

	public static DetailedType classifyType(Class propertyType) {
		if (propertyType != null) {
			if (propertyType.isPrimitive()) {
				if (propertyType.equals(SecondaryIdentifier.BYTE.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_BYTE;
                } else if (propertyType.equals(SecondaryIdentifier.SHORT.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_SHORT;
                } else if (propertyType.equals(SecondaryIdentifier.INT.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_INT;
                } else if (propertyType.equals(SecondaryIdentifier.LONG.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_LONG;
                } else if (propertyType.equals(SecondaryIdentifier.FLOAT.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_FLOAT;
                } else if (propertyType.equals(SecondaryIdentifier.DOUBLE.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_DOUBLE;
                } else if (propertyType.equals(SecondaryIdentifier.CHAR.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_CHAR;
                } else if (propertyType.equals(SecondaryIdentifier.BOOLEAN.getPrimitiveClass())) {
                    return DetailedType.PRIMITIVE_BOOLEAN;
				}
			    return DetailedType.UNKNOWN;
			}
			
			if (propertyType.isEnum()) {
				return DetailedType.ENUM_TYPE;
			}
			
			String javaType = propertyType.getName();
			if (propertyType.isArray()) {
				javaType = removeSpecialChars(javaType);
				String arrayType = javaType.substring(0, 1);
				if (SecondaryIdentifier.BYTE.getArrayElementType().equals(arrayType)) {
					return DetailedType.ARRAY_PRIMITIVE_BYTE;
                } else if (SecondaryIdentifier.SHORT.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_SHORT;
                } else if (SecondaryIdentifier.INT.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_INT;
                } else if (SecondaryIdentifier.LONG.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_LONG;
                } else if (SecondaryIdentifier.FLOAT.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_FLOAT;
                } else if (SecondaryIdentifier.DOUBLE.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_DOUBLE;
                } else if (SecondaryIdentifier.CHAR.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_CHAR;
                } else if (SecondaryIdentifier.BOOLEAN.getArrayElementType().equals(arrayType)) {
                    return DetailedType.ARRAY_PRIMITIVE_BOOLEAN;
                } else if (SecondaryIdentifier.OBJECT.getArrayElementType().equals(arrayType)) {
					return DetailedType.ARRAY_OBJECT_TYPE;
				}
                return DetailedType.UNKNOWN;
			}
            return DetailedType.OBJECT_TYPE;
		}
		return DetailedType.UNKNOWN;
	}
    
    private static String removeSpecialChars(String javaType) {
        javaType = javaType.replace(SYMBOL_SQUARE_BRACKET_OPEN, TXT_NO_SPACE);
        javaType = javaType.replace(SYMBOL_SEMI_COLON, TXT_NO_SPACE);
        return javaType;
    }
	
}
