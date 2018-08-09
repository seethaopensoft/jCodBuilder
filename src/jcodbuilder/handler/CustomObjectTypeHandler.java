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

import jcodbuilder.codegen.ObjectParser;
import jcodbuilder.dos.CustomObjectTypeInfoHolder;
import jcodbuilder.dos.TypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.helper.MethodNameHelper;

/**
 * <pre>
 * <b>Description : </b>
 * This type is the base for defining any custom object handler. The method <code>handleExtraction</code> will be 
 * called during parsing and <code>handleGenerationPreProcess</code> and <code>handleGenerationProcess</code> will be
 * called during source code generation.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public interface CustomObjectTypeHandler {

    public TypeInfoHolder handleExtraction(Class dataType, Object object, ImportStatementHelper importStmtHelper, 
        ObjectParser callbackParser);
    
    public String handleGenerationPreProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        CustomObjectTypeInfoHolder infoHolderParam, String propertyName);
    
    public String handleGenerationProcess(MethodNameHelper methodNameHelper, Set<String> importedClassNames, 
        CustomObjectTypeInfoHolder infoHolderParam);
    
}
