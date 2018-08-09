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
package jcodbuilder.template;

import java.util.Map;
import java.util.TreeMap;

import static jcodbuilder.common.AppConstants.*;
import static jcodbuilder.common.PropertiesBasedAppConstants.INDENT_LENGTH_PER_LEVEL;

/**
 * <pre>
 * <b>Description : </b>
 * Util class for writing the source code instruction.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class TemplateUtil {
    
    private static Map<Integer, String> indentMap = new TreeMap<Integer, String>();
    
    static {
        indentMap.put(0, TXT_NO_SPACE);
        for (int i = 1; i < 2; i++) {
            indentMap.put(i, createIndentFor(i));
        }
    }
    
    public static void emptyLine(StringBuilder instructions) {
        instructions.append(TXT_NEW_LINE);
    }

    public static void write(int indentLevel, StringBuilder instructions, String data) {
        instructions.append(getIndentFor(indentLevel));
        instructions.append(data);
        instructions.append(TXT_NEW_LINE);
    }

    public static String getIndentFor(int level) {
        String value = indentMap.get(level);
        if (value == null) {
            value = createIndentFor(level);
        }
        return value;
    }
    
    private static String createIndentFor(int level) {
        int length = level * INDENT_LENGTH_PER_LEVEL;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(TXT_SINGLE_SPACE);
        }
        return sb.toString();
    }
    
    public static String constructArrayDimensionsBrackets(int arrayDimensions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayDimensions; i++) {
            sb.append(SYMBOL_SQUARE_BRACKET_OPEN_CLOSE);
        }
        return sb.toString();
    }

}
