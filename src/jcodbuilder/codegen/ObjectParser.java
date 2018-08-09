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

import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_LOWER_END;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_LOWER_START;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_UPPER_END;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_UPPER_START;
import static jcodbuilder.common.AppConstants.CHAR_NUMBER_END;
import static jcodbuilder.common.AppConstants.CHAR_NUMBER_START;
import static jcodbuilder.common.AppConstants.STR_PROPERTY_NAME_CLASS;
import static jcodbuilder.common.AppConstants.SYMBOL_COLON_WITH_SPACE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_CLOSE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN;
import static jcodbuilder.common.AppConstants.SYMBOL_SQUARE_BRACKET_OPEN_CLOSE;
import static jcodbuilder.common.AppConstants.TXT_FORMAT_FOR_FLOAT;
import static jcodbuilder.common.AppConstants.TXT_FORMAT_FOR_LONG;
import static jcodbuilder.common.AppConstants.TXT_NO_SPACE;
import static jcodbuilder.common.AppConstants.TXT_SINGLE_SPACE;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jcodbuilder.common.DetailedType;
import jcodbuilder.common.PrimaryIdentifier;
import jcodbuilder.common.SecondaryIdentifier;
import jcodbuilder.dos.ArrayInfoHolder;
import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.dos.SimpleTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.dos.ArrayInfoHolder.ArrayType;
import jcodbuilder.handler.CustomObjectHandlerMapper;
import jcodbuilder.handler.CustomObjectTypeHandler;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.logger.AppLoggerUtil;
import jcodbuilder.util.ImportStatementUtil;
import jcodbuilder.util.ParserUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This class is responsible for parsing the supplied runtime object.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ObjectParser implements Parser {

    private Object objectToBeParsed;

	private ImportStatementHelper importStmtHelper;
	
	public ObjectParser(Object objectToBeParsedParam, ImportStatementHelper importStmtHelperParam) {
		this.objectToBeParsed = objectToBeParsedParam;
		this.importStmtHelper = importStmtHelperParam;
	}
	
	public ObjectTypeInfoHolder parse() {
	    // When expected type is of Object, better to perform earlier check to avoid unnecessary execution.
	    if (ParserUtil.isValidTypeForProcessing(objectToBeParsed.getClass())) {
	        TypeInfoHolder infoHolder = processObjectType(objectToBeParsed.getClass(), objectToBeParsed);
            if (infoHolder instanceof ObjectTypeInfoHolder) {
                return (ObjectTypeInfoHolder) infoHolder;
            }
	    }
        return null;
	}
	
	public TypeInfoHolder process(Class dataType, Object dataValue) {
        TypeInfoHolder propertyInfoHolder = null;
        
        if (dataValue == null) {
            return null;
        }
        
        DetailedType detailedDataType = TypeClassifier.classifyType(dataType);
        if (!DetailedType.UNKNOWN.equals(detailedDataType)) {
            if (PrimaryIdentifier.PRIMITIVE.equals(detailedDataType.getPrimId())) {
                propertyInfoHolder = processPrimitiveType(dataValue, detailedDataType.getSecId());
            } else if (PrimaryIdentifier.OBJECT.equals(detailedDataType.getPrimId())) {
                propertyInfoHolder = processObjectType(dataType, dataValue);
            } else if (PrimaryIdentifier.ENUM.equals(detailedDataType.getPrimId())) {
                propertyInfoHolder = processEnumType(dataValue);
            } else if (PrimaryIdentifier.ARRAY_PRIMITIVE.equals(detailedDataType.getPrimId())) {
                propertyInfoHolder = processPrimitiveArray(dataType, dataValue, detailedDataType);
            } else if (PrimaryIdentifier.ARRAY_OBJECT.equals(detailedDataType.getPrimId())) {
                propertyInfoHolder = processObjectArray(dataType, dataValue);
            }
        }
        return propertyInfoHolder;
	}
	
	private boolean isValidProperty(PropertyDescriptor propertyDesc) {
	    return (propertyDesc.getWriteMethod() != null && propertyDesc.getReadMethod() != null);
	}
	
    private String getPropertyName(PropertyDescriptor propertyDesc) {
        return propertyDesc.getName();
    }
    
    private Class getPropertyType(PropertyDescriptor propertyDesc) {
        return propertyDesc.getPropertyType();
    }
    
    private String getPropertyWriterMethodName(PropertyDescriptor propertyDesc) {
        return propertyDesc.getWriteMethod().getName();
    }
    
	private Object getPropertyValue(Method objGetterMethod, Object obj) {
        try {
            if (objGetterMethod != null && obj != null) {
                return objGetterMethod.invoke(obj, (Object[]) null);
            }
        }
        catch (Exception e) {
            AppLoggerUtil.println("Exception while using getter method : " + objGetterMethod.getName() + " of " 
                + obj.getClass().getCanonicalName());
            e.printStackTrace();
        }
        return null;
	}
	/**
	 * 
	 * <pre>
	 * <b>Description : </b>
     * For Primitive type only, it performs default value check. 
	 * For Object type, default value (null) check is already done at caller side to save some execution. So no need 
	 * to handle it here.
	 * (Note: Primitive array will be considered as Object type)
	 * 
	 * @param dataType
	 * @param dataValue
	 * @return
	 * </pre>
	 */
	private boolean containsDefaultValueForPrimitive(SecondaryIdentifier dataType, Object dataValue) {
        try {
            if (SecondaryIdentifier.BYTE.equals(dataType)
                || SecondaryIdentifier.SHORT.equals(dataType)
                || SecondaryIdentifier.INT.equals(dataType)
                || SecondaryIdentifier.LONG.equals(dataType)
                || SecondaryIdentifier.FLOAT.equals(dataType)
                || SecondaryIdentifier.DOUBLE.equals(dataType)) {
                Number value = (Number) dataValue;
                if (value.doubleValue() == 0) {
                    return true;
                }
            } else if (SecondaryIdentifier.CHAR.equals(dataType)) {
                Character value = (Character) dataValue;
                if (value == Character.MIN_VALUE) {
                    return true;
                }
            } else if (SecondaryIdentifier.BOOLEAN.equals(dataType)) {
                Boolean value = (Boolean) dataValue;
                if (Boolean.FALSE.equals(value)) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            // Mark it as true to stop processing this value
            AppLoggerUtil.println("Exception occured while checking default value : " + e.getMessage() 
                + SYMBOL_COLON_WITH_SPACE + e.getCause() + " -- For Input  " + dataValue);
            return true;
        }
        return false;
	}
	
    private SimpleTypeInfoHolder processPrimitiveType(Object dataValue, SecondaryIdentifier dataType) {
        return processPrimitiveType(dataValue, dataType, false);
    }
	
    private SimpleTypeInfoHolder processPrimitiveType(Object dataValue, SecondaryIdentifier dataType, 
        boolean useCharCasting) {
        if (!containsDefaultValueForPrimitive(dataType, dataValue)) {
            // boolean, int and double don't require any casting.
            String value = dataValue.toString();
            if (SecondaryIdentifier.BYTE.equals(dataType) || SecondaryIdentifier.SHORT.equals(dataType)) {
                value = SYMBOL_PARENTHESIS_OPEN + dataType.getPrimitiveClass().getName() 
                    + SYMBOL_PARENTHESIS_CLOSE + TXT_SINGLE_SPACE + value;
            } else if (SecondaryIdentifier.LONG.equals(dataType)) {
                value = value + TXT_FORMAT_FOR_LONG;
            } else if (SecondaryIdentifier.FLOAT.equals(dataType)) {  // Casting (float) or Suffix Letter (F)
                value = value + TXT_FORMAT_FOR_FLOAT;
            } else if (SecondaryIdentifier.CHAR.equals(dataType)) {
                int tempValue = ((Character) dataValue).charValue();
                // Using Alphabets (upper & lower case) & Numbers used as is and rest as integer equivalent.
                if (!useCharCasting && ((tempValue >= CHAR_NUMBER_START && tempValue <= CHAR_NUMBER_END) 
                    || (tempValue >= CHAR_ALPHABET_UPPER_START && tempValue <= CHAR_ALPHABET_UPPER_END) 
                    || (tempValue >= CHAR_ALPHABET_LOWER_START && tempValue <= CHAR_ALPHABET_LOWER_END))) {
                    value = ParserUtil.wrapSingleQuoteForChar(value);
                } else {
                    value = SYMBOL_PARENTHESIS_OPEN + dataType.getPrimitiveClass().getName() + SYMBOL_PARENTHESIS_CLOSE 
                        + TXT_SINGLE_SPACE + Integer.toString(tempValue);
                }
            }  
            return ParserUtil.createSimpleTypeInfoHolder(null, value, importStmtHelper);
        } else {
            return null;
        }
    }
    
	private SimpleTypeInfoHolder processEnumType(Object propertyValue) {
        Enum enumElement = (Enum) propertyValue;
        SimpleTypeInfoHolder infoHolder = ParserUtil.createSimpleTypeInfoHolder(enumElement.getClass(), 
            enumElement.name(), importStmtHelper);
		infoHolder.setEnumType(true);
		return infoHolder;
	}
	
    private TypeInfoHolder processObjectType(Class objectType, Object object) {
        CustomObjectTypeHandler customObjectTypeHandler = 
            CustomObjectHandlerMapper.checkAndRetrieveCustomObjectTypeHandler(objectType);
        if (customObjectTypeHandler != null) {
            // execute custom object handling for wrapper or collection or Pojo.
            return processCustomObjectType(objectType, object, customObjectTypeHandler);
        } else {
            // execute normal object (Pojo) handling.
            return processPojoType(objectType, object);
        }
    }

    private ObjectTypeInfoHolder processPojoType(Class objectType, Object object) {
        ObjectTypeInfoHolder infoHolder = null;

        try {
            if (ParserUtil.isValidClassType(objectType)) {
                BeanInfo beanInfo = Introspector.getBeanInfo(objectType);
                PropertyDescriptor[] propertyDescs = beanInfo.getPropertyDescriptors();
                infoHolder = ParserUtil.createObjectTypeInfoHolder(objectType, importStmtHelper);
                Map<String, TypeInfoHolder> properties = new LinkedHashMap<String, TypeInfoHolder>();
        
                for (PropertyDescriptor propertyDesc : propertyDescs) {
                    if (propertyDesc != null) {
                        String propertyName = getPropertyName(propertyDesc);
                        Class declaredPropertyType = getPropertyType(propertyDesc);
                        if (isValidProperty(propertyDesc)) {
                            propertyName = getPropertyWriterMethodName(propertyDesc);
                            Object propertyValue = getPropertyValue(propertyDesc.getReadMethod(), object);
                            if (propertyValue != null && declaredPropertyType != null && propertyValue.getClass() != null) {
                                Class runtimePropertyType = propertyValue.getClass();
                                // Runtime of concrete class type or anonymous class assigned to concrete class (but not to root Object)
                                if (!runtimePropertyType.isAnonymousClass() 
                                    || (ParserUtil.isAnonymousClassAssignedToConcreteClassType(runtimePropertyType, declaredPropertyType)
                                        && !declaredPropertyType.equals(Object.class))) {
                                    if (ParserUtil.validateIfInnerClass(objectType, runtimePropertyType)) {
                                        if (!declaredPropertyType.isPrimitive() 
                                            && !runtimePropertyType.isAnonymousClass()) {
                                            // Use runtime type, when it's not anonymous (to cover subclass)
                                            declaredPropertyType = runtimePropertyType;
                                        }
                                        TypeInfoHolder propertyInfoHolder = process(declaredPropertyType, propertyValue);
                                        if (propertyInfoHolder != null) {
                                            properties.put(propertyName, propertyInfoHolder);
                                        }
                                    } else {
                                        AppLoggerUtil.println("Invalid Property (Non-Enclosing Class referencing Inner Class) : " 
                                            + propertyName + SYMBOL_COLON_WITH_SPACE + declaredPropertyType);
                                    }
                                } else {
                                    AppLoggerUtil.println("Invalid Property Value (Anonymous Class of Interface/AbstractClass Type) : " 
                                        + propertyName + SYMBOL_COLON_WITH_SPACE + declaredPropertyType);
                                }
                            }
                        } else {
                            if (!(STR_PROPERTY_NAME_CLASS.equals(propertyName) && Class.class.equals(declaredPropertyType))) {
                                AppLoggerUtil.println("Invalid Property : " + propertyName + SYMBOL_COLON_WITH_SPACE 
                                    + declaredPropertyType);
                            }
                        }
                    } else {
                        AppLoggerUtil.println("Null PropertyDescriptor encountered");
                    }
                }
                if (!properties.isEmpty()) {
                    infoHolder.setProperties(properties);
                }
            } else {
                if (objectType != null) {
                    AppLoggerUtil.println("Local/Non-public Class encountered : " + objectType.getName());
                } else {
                    AppLoggerUtil.println("Local/Non-public Class encountered");
                }
            }
        }
        catch (IntrospectionException e) {
            AppLoggerUtil.println("Unable to introspect the given object");
            e.printStackTrace();
        }
        
        return infoHolder;
    }
    
    private TypeInfoHolder processCustomObjectType(Class objectType, Object objectValue, 
        CustomObjectTypeHandler customObjectTypeHandler) {
        return customObjectTypeHandler.handleExtraction(objectType, objectValue, importStmtHelper, this);
    }
    
    private int getArrayDimensions(Class classType) {
        int index = 0;
        if (classType.isArray()) {
            String arrayType = classType.getName();
            char ch;
            while ((ch = arrayType.charAt(index)) == '[') {
                index++;
            }
        }
        return index; 
    }

    private ArrayInfoHolder processPrimitiveArray(Class arrayClassType, Object arrayObject, 
        DetailedType detailedDataType) {
        return processPrimitiveArray(arrayClassType, arrayObject, detailedDataType, false);
    }
    
    public ArrayInfoHolder processPrimitiveArray(Class arrayClassType, Object arrayObject, 
        DetailedType detailedDataType, boolean useCharCasting) {
        ArrayInfoHolder infoHolder = new ArrayInfoHolder();
        List<TypeInfoHolder> arrayData = new ArrayList<TypeInfoHolder>();
        List<Integer> arrayDataIndices = new ArrayList<Integer>();
        int arrayDimensions = getArrayDimensions(arrayClassType);
        int arraySize = Array.getLength(arrayObject);
        Class primitiveType = detailedDataType.getSecId().getPrimitiveClass();
        infoHolder.setClassName(primitiveType.getName());
        infoHolder.setArrayDimensions(arrayDimensions);
        infoHolder.setArraySize(arraySize);
        infoHolder.setArrayType(ArrayType.SIMPLE_TYPE_ARRAY);
        if (arrayDimensions == 1) {
            for (int index = 0; index < arraySize; index++) {
                Object elementValue = Array.get(arrayObject, index);
                TypeInfoHolder elementProcessedValue = processPrimitiveType(elementValue, detailedDataType.getSecId(), 
                    useCharCasting);
                if (elementProcessedValue != null) {
                    arrayData.add(elementProcessedValue);
                    arrayDataIndices.add(index);
                }
            }
        } else {
            for (int index = 0; index < arraySize; index++) {
                Object elementValue = Array.get(arrayObject, index);
                if (elementValue != null) {
                    arrayData.add(processPrimitiveArray(elementValue.getClass(), elementValue, detailedDataType, 
                        useCharCasting));
                    arrayDataIndices.add(index);
                }
            }
        }
        infoHolder.setArrayData(arrayData);
        infoHolder.setArrayDataIndices(arrayDataIndices);
        return infoHolder;
    }
    
    private ArrayInfoHolder processObjectArray(Class arrayClassType, Object arrayObject) {
        ArrayInfoHolder infoHolder = new ArrayInfoHolder();
        List<TypeInfoHolder> arrayData = new ArrayList<TypeInfoHolder>();
        List<Integer> arrayDataIndices = new ArrayList<Integer>();
        int arrayDimensions = getArrayDimensions(arrayClassType);
        int arraySize = Array.getLength(arrayObject);
        String[] qualifierAndClassName = ImportStatementUtil.extractQualifierAndClassName(arrayClassType);
        qualifierAndClassName[1] = removeSpecialCharsForClass(qualifierAndClassName[1]);
        infoHolder.setQualifier(qualifierAndClassName[0]);
        infoHolder.setClassName(qualifierAndClassName[1]);
        importStmtHelper.addQualifiedClassName(qualifierAndClassName[0], qualifierAndClassName[1]);
        infoHolder.setArrayDimensions(arrayDimensions);
        infoHolder.setArraySize(arraySize);
        infoHolder.setArrayType(ArrayType.OBJECT_TYPE_ARRAY);
        if (arrayDimensions == 1) {
            for (int index = 0; index < arraySize; index++) {
                Object elementValue = Array.get(arrayObject, index);
                if (elementValue != null) {
                    arrayData.add(process(elementValue.getClass(), elementValue));
                    arrayDataIndices.add(index);
                }
            }
        } else {
            for (int index = 0; index < arraySize; index++) {
                Object elementValue = Array.get(arrayObject, index);
                if (elementValue != null) {
                    arrayData.add(processObjectArray(elementValue.getClass(), elementValue));
                    arrayDataIndices.add(index);
                }
            }
        }
        if (arrayData.size() > 0) {
            infoHolder.setArrayData(arrayData);
            infoHolder.setArrayDataIndices(arrayDataIndices);
        }
        return infoHolder;
    }
    
    private String removeSpecialCharsForClass(String className) {
        className = className.replace(SYMBOL_SQUARE_BRACKET_OPEN_CLOSE, TXT_NO_SPACE);
        return className;
    }

}
