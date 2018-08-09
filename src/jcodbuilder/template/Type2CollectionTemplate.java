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

import static jcodbuilder.common.AppConstants.*;

import java.util.Set;

import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.util.ImportStatementUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This class writes the source code instruction to build the collection data of <code>Map</code> type.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type2CollectionTemplate {
    
    public static void writeHeaderSection(CustomObjectTypeInfoHolder infoHolder, StringBuilder instructions, 
        Set<String> importedClassNames) {
        String qualifiedClassName = ImportStatementUtil.getQualifiedClassName(infoHolder.getQualifier(), 
            infoHolder.getClassName(), importedClassNames);
        StringBuilder instData = new StringBuilder();

        instData.append(KEYWORD_MODIFIER_PRIVATE_WITH_SPACE);
        instData.append(KEYWORD_MODIFIER_STATIC_WITH_SPACE);
        instData.append(qualifiedClassName);
        instData.append(TXT_SINGLE_SPACE);
        instData.append(infoHolder.getAllocatedMethodName());
        instData.append(SYMBOL_PARENTHESIS_OPEN_CLOSE);
        instData.append(TXT_SINGLE_SPACE);
        instData.append(SYMBOL_CURLY_BRACES_OPEN);
        TemplateUtil.write(1, instructions, instData.toString());
        
        instData.setLength(0);
        instData.append(qualifiedClassName);
        instData.append(TXT_SINGLE_SPACE);
        instData.append(infoHolder.getLocalVariableName());
        instData.append(SYMBOL_EQUALS_WITH_SPACE);
        instData.append(KEYWORD_NEW_WITH_SPACE);
        instData.append(qualifiedClassName);
        instData.append(SYMBOL_PARENTHESIS_OPEN_CLOSE);
        instData.append(SYMBOL_SEMI_COLON);
        TemplateUtil.write(2, instructions, instData.toString());
    }
    
    public static void writeBodySection(CustomObjectTypeInfoHolder infoHolder, StringBuilder instructions, String key, 
        String value) {
        StringBuilder instData = new StringBuilder();
        
        instData.append(infoHolder.getLocalVariableName());
        instData.append(SYMBOL_DOT);
        instData.append(STR_TYPE2_COLLECTION_METHOD_NAME);
        instData.append(SYMBOL_PARENTHESIS_OPEN);
        instData.append(key);
        instData.append(SYMBOL_COMMA_WITH_SPACE);
        instData.append(value);
        instData.append(SYMBOL_PARENTHESIS_CLOSE);
        instData.append(SYMBOL_SEMI_COLON);
        TemplateUtil.write(2, instructions, instData.toString());
    }
    
    public static void writeFooterSection(CustomObjectTypeInfoHolder infoHolder, StringBuilder instructions) {
        StringBuilder instData = new StringBuilder();
        
        instData.append(KEYWORD_RETURN_WITH_SPACE);
        instData.append(infoHolder.getLocalVariableName());
        instData.append(SYMBOL_SEMI_COLON);
        TemplateUtil.write(2, instructions, instData.toString());
        
        TemplateUtil.write(1, instructions, SYMBOL_CURLY_BRACES_CLOSE);
        TemplateUtil.emptyLine(instructions);        
    }
    
}
