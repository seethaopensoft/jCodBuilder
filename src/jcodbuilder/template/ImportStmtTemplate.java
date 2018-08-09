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

/**
 * <pre>
 * <b>Description : </b>
 * This class writes the source code instruction for import statement.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ImportStmtTemplate {

    public static void writeBodySection(StringBuilder instructions, Set<String> importClassNames) {
        if (importClassNames != null && !importClassNames.isEmpty()) {
            StringBuilder instData = new StringBuilder();
            
            for (String stmt : importClassNames) {
                instData.setLength(0);
                instData.append(KEYWORD_IMPORT_WITH_SPACE);
                instData.append(stmt);
                instData.append(SYMBOL_SEMI_COLON);
                TemplateUtil.write(0, instructions, instData.toString());
            }
            TemplateUtil.emptyLine(instructions);
        }
    }
}
