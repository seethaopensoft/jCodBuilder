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

import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_LOWER_END;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_LOWER_START;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_UPPER_END;
import static jcodbuilder.common.AppConstants.CHAR_ALPHABET_UPPER_START;
import static jcodbuilder.common.AppConstants.CHAR_NUMBER_END;
import static jcodbuilder.common.AppConstants.CHAR_NUMBER_START;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_CLOSE;
import static jcodbuilder.common.AppConstants.SYMBOL_PARENTHESIS_OPEN;
import static jcodbuilder.common.AppConstants.TXT_SINGLE_SPACE;

import java.util.Set;

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.util.ParserUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This covers Character only
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class CharacterWrapperHandler implements CustomObjectTypeHandler {

    @Override
    public TypeInfoHolder handleExtraction(Class dataType, Object object, ImportStatementHelper importStmtHelper,
        ObjectParser callbackParser) {
        if (object != null) {
            String value = ((Character) object).toString();
            int tempValue = ((Character) object).charValue();
            // Using Alphabets (upper & lower case) & Numbers used as is and rest as integer equivalent.
            if ((tempValue >= CHAR_NUMBER_START && tempValue <= CHAR_NUMBER_END) 
                || (tempValue >= CHAR_ALPHABET_UPPER_START && tempValue <= CHAR_ALPHABET_UPPER_END) 
                || (tempValue >= CHAR_ALPHABET_LOWER_START && tempValue <= CHAR_ALPHABET_LOWER_END)) {
                value = ParserUtil.wrapSingleQuoteForChar(value);
            } else {
                value = SYMBOL_PARENTHESIS_OPEN + char.class.getName() + SYMBOL_PARENTHESIS_CLOSE 
                    + TXT_SINGLE_SPACE + Integer.toString(tempValue);
            }
            return ParserUtil.createSimpleTypeInfoHolder(dataType, value, importStmtHelper);
        }
        return null;
    }

    @Override
    public String handleGenerationPreProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam, String propertyName) {
        return null;
    }

    @Override
    public String handleGenerationProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam) {
        return null;
    }

}
