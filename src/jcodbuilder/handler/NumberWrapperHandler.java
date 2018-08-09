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

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.SimpleTypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.util.ParserUtil;
import static jcodbuilder.common.AppConstants.TXT_FORMAT_FOR_LONG;

/**
 * <pre>
 * <b>Description : </b>
 * This covers All Number types, including BigXXX and AtomicXXX versions
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class NumberWrapperHandler implements CustomObjectTypeHandler {

    @Override
    public SimpleTypeInfoHolder handleExtraction(Class dataType, Object object, ImportStatementHelper importStmtHelper, 
        ObjectParser callbackParser) {
        if (object != null) {
            String value = object.toString();
            if (!(object instanceof AtomicInteger || object instanceof AtomicLong)) {
                value = ParserUtil.wrapDoubleQuoteForValue(value);
            } else if (object instanceof AtomicLong) {
                value = value + TXT_FORMAT_FOR_LONG;
            }
            return ParserUtil.createSimpleTypeInfoHolder(dataType, value, 
                importStmtHelper);
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
