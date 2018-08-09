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
package jcodbuilder.common;

import static jcodbuilder.common.AppConstants.PROP_FILE_NAME_APP_PROPS;
import static jcodbuilder.common.AppConstants.STRING_HANDLING_MODE;
import static jcodbuilder.common.AppConstants.STRING_HANDLING_MODE_CHAR;
import static jcodbuilder.common.AppConstants.STRING_HANDLING_MODE_USUAL;

import java.util.Properties;

import jcodbuilder.logger.AppLoggerUtil;
import jcodbuilder.util.PathInfo;
import jcodbuilder.util.PropertiesLoader;

/**
 * <pre>
 * <b>Description : </b>
 * This contains the constants used by the application initialized from the property file. The constants are loaded from
 * these locations - firstly, from within jar under resources directory, secondly, from "user.home" directory. So, that 
 * means entry in "user.home" directory will take precedence over "resources" folder.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class PropertiesBasedAppConstants {

    // +++++ All keys of App Properties +++++
    private static final String PROP_KEY_CLASS_SEPARATOR = "CLASS_SEPARATOR";

    private static final String PROP_KEY_INDENT_LENGTH_PER_LEVEL = "INDENT_LENGTH_PER_LEVEL";

    private static final String PROP_KEY_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR 
        = "COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR";

    // +++++ Default Value for Constants +++++
    private static final String DEFAULT_CLASS_SEPARATOR = ".";
    
    private static final int DEFAULT_INDENT_LENGTH_PER_LEVEL = 4;

    private static final int DEFAULT_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR = 10;
    
    // +++++ Constants +++++
    public static final String CLASS_SEPARATOR;
    
    public static final int INDENT_LENGTH_PER_LEVEL;
    
    public static final String STRING_HANDLING_MODE_SELECTED;
    
    public static final int COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR;
    
    static {
        Properties prop = new Properties();
        prop.setProperty(PROP_KEY_CLASS_SEPARATOR, DEFAULT_CLASS_SEPARATOR);
        prop.setProperty(PROP_KEY_INDENT_LENGTH_PER_LEVEL, String.valueOf(DEFAULT_INDENT_LENGTH_PER_LEVEL));
        prop.setProperty(PROP_KEY_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR, 
            String.valueOf(DEFAULT_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR));

        prop = PropertiesLoader.loadStandardPropertyFile(PathInfo.INTERNAL_PATH, PROP_FILE_NAME_APP_PROPS, prop);
        prop = PropertiesLoader.loadStandardPropertyFile(PathInfo.EXTERNAL_PATH, PROP_FILE_NAME_APP_PROPS, prop);
        
        CLASS_SEPARATOR = prop.getProperty(PROP_KEY_CLASS_SEPARATOR);
        
        String propValue = prop.getProperty(PROP_KEY_INDENT_LENGTH_PER_LEVEL);
        int propValueInInt = -1;
        try {
            int tempPropValueInInt = Integer.parseInt(propValue);
            if (tempPropValueInInt > 0) {
                propValueInInt = tempPropValueInInt;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            AppLoggerUtil.println("Invalid Value for Property [" + PROP_KEY_INDENT_LENGTH_PER_LEVEL + "] in file : " 
                + PROP_FILE_NAME_APP_PROPS + ". Using Default Value : " + DEFAULT_INDENT_LENGTH_PER_LEVEL);
            propValueInInt = DEFAULT_INDENT_LENGTH_PER_LEVEL;
        }
        INDENT_LENGTH_PER_LEVEL = propValueInInt;
        
        propValue = System.getProperty(STRING_HANDLING_MODE);
        String genMode = STRING_HANDLING_MODE_USUAL; 
        if (propValue != null && propValue.equalsIgnoreCase(STRING_HANDLING_MODE_CHAR)) {
            genMode = STRING_HANDLING_MODE_CHAR;
        }
        STRING_HANDLING_MODE_SELECTED = genMode;
        
        propValue = prop.getProperty(PROP_KEY_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR);
        try {
            int tempPropValueInInt = Integer.parseInt(propValue);
            if (tempPropValueInInt > 0) {
                propValueInInt = tempPropValueInInt;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            AppLoggerUtil.println("Invalid Value for Property [" 
                + PROP_KEY_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR 
                + "] in file : " + PROP_FILE_NAME_APP_PROPS + ". Using Default Value : " 
                + DEFAULT_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR);
            propValueInInt = DEFAULT_COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR;
        }
        COUNT_FOR_ARRAY_ELEMENT_PER_LINE_WHEN_STRING_HANDLING_MODE_CHAR = propValueInInt;
        
    }
    
    
}
