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

import java.util.Set;

import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.template.TemplateUtil;
import jcodbuilder.util.ImportStatementUtil;
import static jcodbuilder.common.AppConstants.*;

/**
 * <pre>
 * <b>Description : </b>
 * This class writes the source code instruction to build the object.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ObjectTemplate {

    public static void writeHeaderSection(ObjectTypeInfoHolder infoHolder, StringBuilder instructions, 
        String modifierType, Set<String> importedClassNames) {
        String qualifiedClassName = ImportStatementUtil.getQualifiedClassName(infoHolder.getQualifier(), 
            infoHolder.getClassName(), importedClassNames);
        StringBuilder instData = new StringBuilder();
        
        if (infoHolder.getEnclosingClassName() == null) {
            instData.append(modifierType);
            instData.append(TXT_SINGLE_SPACE);
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
        } else {
            instData.append(modifierType);
            instData.append(TXT_SINGLE_SPACE);
            instData.append(qualifiedClassName);
            instData.append(TXT_SINGLE_SPACE);
            instData.append(infoHolder.getAllocatedMethodName());
            instData.append(SYMBOL_PARENTHESIS_OPEN);
            instData.append(infoHolder.getEnclosingClassName());
            instData.append(TXT_SINGLE_SPACE);
            instData.append(STR_PARAMETER_NAME_FOR_ENCLOSING_OBJECT);
            instData.append(SYMBOL_PARENTHESIS_CLOSE);
            instData.append(TXT_SINGLE_SPACE);
            instData.append(SYMBOL_CURLY_BRACES_OPEN);
            TemplateUtil.write(1, instructions, instData.toString());
            
            instData.setLength(0);
            instData.append(qualifiedClassName);
            instData.append(TXT_SINGLE_SPACE);
            instData.append(infoHolder.getLocalVariableName());
            instData.append(SYMBOL_EQUALS_WITH_SPACE);
            instData.append(STR_PARAMETER_NAME_FOR_ENCLOSING_OBJECT);
            instData.append(SYMBOL_DOT);
            instData.append(KEYWORD_NEW_WITH_SPACE);
            instData.append(qualifiedClassName);
            instData.append(SYMBOL_PARENTHESIS_OPEN_CLOSE);
            instData.append(SYMBOL_SEMI_COLON);
            TemplateUtil.write(2, instructions, instData.toString());
        }
    }

    public static void writeBodySection(ObjectTypeInfoHolder infoHolder, StringBuilder instructions, 
        String[] propertyNameValuePair) {
        StringBuilder instData = new StringBuilder();

        instData.append(infoHolder.getLocalVariableName());
        instData.append(SYMBOL_DOT);
        instData.append(propertyNameValuePair[0]);
        instData.append(SYMBOL_PARENTHESIS_OPEN);
        instData.append(propertyNameValuePair[1]);
        instData.append(SYMBOL_PARENTHESIS_CLOSE);
        instData.append(SYMBOL_SEMI_COLON);
        TemplateUtil.write(2, instructions, instData.toString());
    }

    public static void writeFooterSection(ObjectTypeInfoHolder infoHolder, StringBuilder instructions) {
        StringBuilder instData = new StringBuilder();

        instData.append(KEYWORD_RETURN_WITH_SPACE);
        instData.append(infoHolder.getLocalVariableName());
        instData.append(SYMBOL_SEMI_COLON);
        TemplateUtil.write(2, instructions, instData.toString());
        
        TemplateUtil.write(1, instructions, SYMBOL_CURLY_BRACES_CLOSE);
        TemplateUtil.emptyLine(instructions);
    }
    
    
    
}
