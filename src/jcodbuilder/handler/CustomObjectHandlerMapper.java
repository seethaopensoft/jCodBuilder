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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import jcodbuilder.common.AppConstants;
import jcodbuilder.logger.AppLoggerUtil;
import jcodbuilder.util.PathInfo;
import jcodbuilder.util.PropertiesLoader;

/**
 * <pre>
 * <b>Description : </b>
 * This will be built once and same instance will be shared across multiple invocation. 
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class CustomObjectHandlerMapper {
    
    private static Map<String, String> classNameLookupForExactMatch = new LinkedHashMap<String, String>();

    private static Map<Class, String> classLookupForInstanceofMatch = new LinkedHashMap<Class, String>();

    private static Map<String, CustomObjectTypeHandler> mappedUniqueHandlerReference = new TreeMap<String, 
        CustomObjectTypeHandler>();
    
    static {
        initializeHandlerMap();
    }
    
    private static void initializeHandlerMap() {
        LinkedHashMap<String, String> externalPropsMapperForExact = PropertiesLoader.loadMapBasedPropertyFile(
            PathInfo.EXTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_EXACT);
        LinkedHashMap<String, String> externalPropsMapperForInstanceof = PropertiesLoader.loadMapBasedPropertyFile(
            PathInfo.EXTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_INSTANCEOF);
        LinkedHashMap<String, String> internalPropsMapperForExact = PropertiesLoader.loadMapBasedPropertyFile(
            PathInfo.INTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_EXACT);
        LinkedHashMap<String, String> internalPropsMapperForInstanceof = PropertiesLoader.loadMapBasedPropertyFile(
            PathInfo.INTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_INSTANCEOF);

        loadHandlers(externalPropsMapperForExact, classNameLookupForExactMatch, mappedUniqueHandlerReference, 
            PathInfo.EXTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_EXACT, true);
        loadHandlers(externalPropsMapperForInstanceof, classLookupForInstanceofMatch, 
            mappedUniqueHandlerReference, PathInfo.EXTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_INSTANCEOF,
            false);
        loadHandlers(internalPropsMapperForExact, classNameLookupForExactMatch, mappedUniqueHandlerReference,
            PathInfo.INTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_EXACT, true);
        loadHandlers(internalPropsMapperForInstanceof, classLookupForInstanceofMatch, 
            mappedUniqueHandlerReference, PathInfo.INTERNAL_PATH, AppConstants.PROP_FILE_NAME_HANDLER_MAPPER_INSTANCEOF,
            false);
    }

    private static void loadHandlers(Map<String, String> prop, Map classLookup, Map mappedUniqueHandlerReference, 
        PathInfo pathInfo, String propFilePath, boolean isExactMatchType) {
        if (prop != null) {
            Set<Entry<String, String>> propEntries = prop.entrySet();
            String entryKey = null;
            String entryValue = null;
            for (Entry<String, String> entry : propEntries) {
                entryKey = entry.getKey();
                entryValue = entry.getValue();
                if (validateClassName(entryKey) && validateClassName(entryValue)) {
                    Object newEntryKey = getEntryKeyIfEntryNotPresent(classLookup, entryKey, entryValue, 
                        isExactMatchType);
                    if (newEntryKey != null) {
                        if (!mappedUniqueHandlerReference.containsKey(entryValue)) {
                            Object handlerReference = createInstanceFor(entryValue, propFilePath, entryKey);
                            if (handlerReference != null) {
                                classLookup.put(newEntryKey, entryValue);                                
                                mappedUniqueHandlerReference.put(entryValue, handlerReference);
                            }
                        } else {
                            classLookup.put(newEntryKey, entryValue);                                
                        }
                    }
                } else {
                    AppLoggerUtil.println("Property Key and/or Value is empty. Prop Key : " + entryKey 
                        + ", Prop Value : " + entryValue + ", File : " + pathInfo.getRootPath() + propFilePath);
                }
            }
        }
    }
    
    private static boolean validateClassName(String entryKey) {
        return (entryKey != null && entryKey.trim().length() > 0);
    }

    private static Object getEntryKeyIfEntryNotPresent(Map classLookup, String entryKey, String entryValue, 
        boolean isExactMatchType) {
        Object entryKeyTemp;
        if (isExactMatchType) {
            entryKeyTemp = entryKey;
        } else {
            entryKeyTemp = loadClassObject(entryKey);
        }
        if (!classLookup.containsKey(entryKeyTemp)) {
            return entryKeyTemp;
        } else {
            return null;
        }
    }
    
    private static Object createInstanceFor(String fullyQualifiedClassName, String propFilePath, 
        String entryKeyForInfo) {
        Class classObject = loadClassObject(fullyQualifiedClassName); 
        if (classObject != null) {
            if (CustomObjectTypeHandler.class.isAssignableFrom(classObject)) {
                try {
                    return classObject.newInstance();
                }
                catch (Exception e) {
                    AppLoggerUtil.println("Unable to create handler reference for [" + entryKeyForInfo + " : " 
                        + fullyQualifiedClassName  + "], Property File [" + propFilePath 
                        + "]. Issue while instantiating the class. Cause : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                AppLoggerUtil.println("Unable to create handler reference for [" + entryKeyForInfo + " : " 
                    + fullyQualifiedClassName + "], Property File [" + propFilePath 
                    + "]. Given class is not subclass of CustomObjectTypeHandler.class");
            }
        } else {
            AppLoggerUtil.println("Unable to create handler reference for [" + entryKeyForInfo + " : " 
                + fullyQualifiedClassName + "]");
        }
        return null;
    }
    
    /**
     * 
     * <pre>
     * <b>Description : </b>
     * It will try to load the class from the current class loader (classpath). If any error occured during the load, 
     * it will return 'null'. 
     * 
     * @param fullyQualifiedClassName
     * @return
     * </pre>
     */
    private static Class loadClassObject(String fullyQualifiedClassName) {
        try {
            return Class.forName(fullyQualifiedClassName);
        } catch (Exception e) {
            AppLoggerUtil.println("Unable to load class file [" + fullyQualifiedClassName + "]");
            return null;
        }
    }
    
    public static CustomObjectTypeHandler checkAndRetrieveCustomObjectTypeHandler(Class objectType) {
        String fullyQualifiedClassName = objectType.getCanonicalName();
        String handlerReferenceKey = classNameLookupForExactMatch.get(fullyQualifiedClassName);
        if (handlerReferenceKey != null) {
            return mappedUniqueHandlerReference.get(handlerReferenceKey);
        }
        Set<Entry<Class, String>> instanceofEntries = classLookupForInstanceofMatch.entrySet();
        Class instanceofEntryKey = null;
        String instanceofEntryValue = null;
        for (Entry<Class, String> instanceofEntry : instanceofEntries) {
            instanceofEntryKey = instanceofEntry.getKey();
            instanceofEntryValue = instanceofEntry.getValue();
            if (instanceofEntryKey.isAssignableFrom(objectType)) {
                handlerReferenceKey = instanceofEntryValue;
                break;
            }
        }
        if (handlerReferenceKey != null) {
            return mappedUniqueHandlerReference.get(handlerReferenceKey);
        }
        return null;
    }

}

