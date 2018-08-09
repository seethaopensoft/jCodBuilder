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
import jcodbuilder.dos.ClassInfoHolder;

/**
 * <pre>
 * <b>Description : </b>
 * This class writes the source code instruction for class definition.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ClassTemplate {
    public static void writeHeaderSection(ClassInfoHolder infoHolder, StringBuilder instructions) {
        StringBuilder instData = new StringBuilder();
        
        instData.append(KEYWORD_MODIFIER_PUBLIC_WITH_SPACE);
        instData.append(KEYWORD_CLASS_WITH_SPACE);
        instData.append(infoHolder.getClassName());
        instData.append(TXT_SINGLE_SPACE);
        instData.append(SYMBOL_CURLY_BRACES_OPEN);
        TemplateUtil.write(0, instructions, instData.toString());
        
        TemplateUtil.emptyLine(instructions);
    }

    public static void writeFooterSection(StringBuilder instructions) {
        TemplateUtil.write(0, instructions, SYMBOL_CURLY_BRACES_CLOSE);
    }

}
