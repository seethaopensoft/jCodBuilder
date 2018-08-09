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

import static jcodbuilder.common.PropertiesBasedAppConstants.STRING_HANDLING_MODE_SELECTED;

import java.util.Set;

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.common.AppConstants;
import jcodbuilder.dos.ArrayInfoHolder;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.helper.MethodNameHelper.MethodNameType;
import jcodbuilder.util.ParserUtil;
import jcodbuilder.writer.Type1StringWriter;

/**
 * <pre>
 * <b>Description : </b>
 * This covers String.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class Type1StringWrapperHandler implements CustomObjectTypeHandler {

    @Override
    public TypeInfoHolder handleExtraction(Class dataType, Object object, ImportStatementHelper importStmtHelper,
        ObjectParser callbackParser) {
        if (STRING_HANDLING_MODE_SELECTED.equals(AppConstants.STRING_HANDLING_MODE_USUAL)) {
            if (object instanceof String) {
                return ParserUtil.createSimpleTypeInfoHolder(null, 
                    ParserUtil.splitStringDataIntoMultiLinesString(object.toString(), 
                        AppConstants.DEFAULT_INDENT_LEVEL_FOR_STRING_SPLIT_INTO_MULTI_LINE), importStmtHelper);
            }
        } else {
            if (object instanceof String) {
                char[] stringData = ((String) object).toCharArray();
                CustomObjectTypeInfoHolder infoHolder = ParserUtil.createCustomObjectTypeInfoHolder(dataType, 
                    importStmtHelper, this, AppConstants.STR_LOCAL_VARIABLE_NAME_FOR_OBJECT);
                if (stringData.length > 0) {
                    TypeInfoHolder arrayInfoHolder = callbackParser.process(stringData.getClass(), stringData); 
                    if (arrayInfoHolder instanceof ArrayInfoHolder) {
                        ((ArrayInfoHolder) arrayInfoHolder).setJoinMultiLinesIntoSingleLine(true);
                        infoHolder.setCustomDataHolder(arrayInfoHolder);
                    }
                }
                return infoHolder;
            }
        }
        return null;
    }

    @Override
    public String handleGenerationPreProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam, String propertyName) {
        String methodName = methodNameHelper.getMethodName(MethodNameType.METHOD_NAME_FOR_OBJECT, 
            infoHolderParam.getClassName());
        return methodName;
    }

    @Override
    public String handleGenerationProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames,
        CustomObjectTypeInfoHolder infoHolderParam) {
        Type1StringWriter writer = new Type1StringWriter(methodNameHelper, importedClassNames, infoHolderParam);
        writer.process();
        return writer.getInstruction();
    }

}
