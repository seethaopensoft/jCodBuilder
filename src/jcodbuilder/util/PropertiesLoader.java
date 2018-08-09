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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Scanner;

import jcodbuilder.common.AppConstants;
import jcodbuilder.logger.AppLoggerUtil;

/**
 * <pre>
 * <b>Description : </b>
 * Util class to load the properties defined in different format.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class PropertiesLoader {

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This uses the <code>java.util.Properties</code> to build the properties map. If entry key repeats again, 
     * corresponding value would be updated against the key. 
     * 
     * New <code>Properties</code> object would be created and the same would be returned back.
     * 
     * @param pathInfo
     * @param filePath
     * @return
     * </pre>
     */
    public static Properties loadStandardPropertyFile(PathInfo pathInfo, String filePath) {
        Properties prop = new Properties();
        return loadStandardPropertyFile(pathInfo, filePath, prop);
    }
    
    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This uses the <code>java.util.Properties</code> to build the properties map. If entry key repeats again, 
     * corresponding value would be updated against the key. 
     * 
     * The returned <code>Properties</code> object is same as the one passed in to the method. If given 'prop' is null, 
     * it will throw <code>NullPointerException</code> 
     * 
     * @param pathInfo
     * @param filePath
     * @param prop
     * @return
     * </pre>
     */
	public static Properties loadStandardPropertyFile(PathInfo pathInfo, String filePath, Properties prop) {
	    if (prop == null) {
	        throw new NullPointerException("Input to the 'prop' parameter is null");
	    }
	    InputStream stream = null;
	    String fullPath = pathInfo.getRootPath() + filePath;
	    
	    if (PathInfo.INTERNAL_PATH.equals(pathInfo)) {
	        stream = PropertiesLoader.class.getResourceAsStream(fullPath);
	    } else {
            if (new File(fullPath).exists()) {
                try {
                    stream = new FileInputStream(fullPath);
                } catch (Exception e) {
                    AppLoggerUtil.println("Unable to load prop file : " + fullPath);
                    return null;
                }
            }
	    }
        try {
            if (stream != null) {
                prop.load(stream);
            }
        } catch (Exception e) {
            AppLoggerUtil.println("Unable to load prop file : " + fullPath);
        } finally {
            try { stream.close(); } catch (Exception e) {}
        }
        return prop;
	}
	
	/**
	 * 
	 * <pre>
	 * <b>Description : </b>
	 * This will create new <code>LinkedHashMap</code> and load the entries in a key-value pair format, from the 
	 * property file in the order of its definition.
	 * 
     * This method will call the overloaded method and pass the value 'false' for 'overrideFlag'. Refer the overloaded 
     * method comments for more details.
	 * 
	 * @param pathInfo
	 * @param filePath
	 * @return
	 * </pre>
	 */
    public static LinkedHashMap<String, String> loadMapBasedPropertyFile(PathInfo pathInfo, String filePath) {
        LinkedHashMap<String, String> propertyMap = new LinkedHashMap<>();
        return loadMapBasedPropertyFile(pathInfo, filePath, propertyMap, false);
    }
    
    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This will load the entries in a key-value pair format, into given <code>LinkedHashMap</code> from the property 
     * file in the order of its definition. If either key or value is empty, then that one won't be loaded. The entry's 
     * key and value will be trimmed before use (both comparison and load).
     * 
     * If 'overrideFlag' is true, it will update the entry again with new value if entry key repeats. Otherwise, 
     * it won't.
     * 
     * It will throw <code>NullPointerException</code> if given 'propertyMap' is null.
     * 
     * @param pathInfo
     * @param filePath
     * @param propertyMap
     * @param overrideFlag
     * @return
     * </pre>
     */
    public static LinkedHashMap<String, String> loadMapBasedPropertyFile(PathInfo pathInfo, String filePath, 
        LinkedHashMap<String, String> propertyMap, boolean overrideFlag) {
        if (propertyMap == null) {
            throw new NullPointerException("Input to the 'propertyMap' parameter is null");
        }
        InputStream stream = null;
        String fullPath = pathInfo.getRootPath() + filePath;
        
        if (PathInfo.INTERNAL_PATH.equals(pathInfo)) {
            stream = PropertiesLoader.class.getResourceAsStream(fullPath);
        } else {
            if (new File(fullPath).exists()) {
                try {
                    stream = new FileInputStream(fullPath);
                } catch (Exception e) {
                    AppLoggerUtil.println("Unable to load prop file : " + fullPath);
                    return null;
                }
            }
        }
        try {
            if (stream != null) {
                Scanner scanner = new Scanner(stream);
                String lineData = null;
                String propertyKey = null;
                String propertyValue = null;
                
                while (scanner.hasNextLine()) {
                    lineData = scanner.nextLine();
                    if (lineData != null && lineData.trim().length() > 0) {
                        String[] property = lineData.split(AppConstants.SYMBOL_EQUALS);
                        if (property[0] != null && property[0].trim().length() > 0 
                            && property[1] != null && property[1].trim().length() > 0) {
                            propertyKey = property[0].trim();
                            propertyValue = property[1].trim();
                            if (!propertyMap.containsKey(propertyKey) || overrideFlag) {
                                propertyMap.put(propertyKey, propertyValue);
                            }
                        } else {
                            AppLoggerUtil.println("Invalid Property Entry [" + lineData + "] in file : " + fullPath);
                        }
                    }
                }
                if (!propertyMap.isEmpty()) {
                    return propertyMap;
                }
            }
        } catch (Exception e) {
            AppLoggerUtil.println("Unable to load prop file : " + fullPath);
        } finally {
            try { stream.close(); } catch (Exception e) {}
        }
        return null;
    }

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This will create new <code>LinkedHashSet</code> and load the entries from the property file in the order of its 
     * definition.
     * 
     * This method will call the overloaded method. Refer the overloaded method comments for more details.
     * 
     * @param pathInfo
     * @param filePath
     * @return
     * </pre>
     */
    public static LinkedHashSet<String> loadSetBasedPropertyFile(PathInfo pathInfo, String filePath) {
        LinkedHashSet<String> propertySet = new LinkedHashSet<String>();
        return loadSetBasedPropertyFile(pathInfo, filePath, propertySet);
    }
    
    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This will load the entries into given <code>LinkedHashSet</code> from the property file in the order of its 
     * definition. Each line would be considered as an entry and would be loaded, if it's not empty. The entry value 
     * will be trimmed before use (both comparison and load).
     * 
     * It will throw <code>NullPointerException</code> if given 'propertySet' is null.
     * 
     * @param pathInfo
     * @param filePath
     * @param propertySet
     * @param overrideFlag
     * @return
     * </pre>
     */
    public static LinkedHashSet<String> loadSetBasedPropertyFile(PathInfo pathInfo, String filePath, 
        LinkedHashSet<String> propertySet) {
        if (propertySet == null) {
            throw new NullPointerException("Input to the 'propertySet' parameter is null");
        }
        InputStream stream = null;
        String fullPath = pathInfo.getRootPath() + filePath;
        
        if (PathInfo.INTERNAL_PATH.equals(pathInfo)) {
            stream = PropertiesLoader.class.getResourceAsStream(fullPath);
        } else {
            if (new File(fullPath).exists()) {
                try {
                    stream = new FileInputStream(fullPath);
                } catch (Exception e) {
                    AppLoggerUtil.println("Unable to load prop file : " + fullPath);
                    return null;
                }
            }
        }
        try {
            if (stream != null) {
                Scanner scanner = new Scanner(stream);
                String lineData = null;
                while (scanner.hasNextLine()) {
                    lineData = scanner.nextLine();
                    if (lineData != null && lineData.trim().length() > 0) {
                        propertySet.add(lineData);
                    }
                }
                if (!propertySet.isEmpty()) {
                    return propertySet;
                }
            }
        } catch (Exception e) {
            AppLoggerUtil.println("Unable to load prop file : " + fullPath);
        } finally {
            try { stream.close(); } catch (Exception e) {}
        }
        return null;
    }
	
	/**
	 * 
	 * <pre>
	 * <b>Description : </b>
	 * Helper method to retrieve property value
	 * 
	 * @param prop
	 * @param key
	 * @return
	 * </pre>
	 */
	public static String getPropertyValue(Properties prop, String key) {
	    String propValue = null;
	    if (prop != null) {
	        propValue = prop.getProperty(key);
        }
	    return propValue;
	}
	
}
