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
package jcodbuilder.codegen;

import java.util.Set;

import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.helper.ImportStatementHelper;
import jcodbuilder.logger.AppLoggerUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This class is the main class responsible for generating the java source code that can be used to reconstruct the 
 * supplied object at runtime. This is more of like marshaling the runtime object graph in the form of java source code.
 * The generated source code will act like object builder for one specific runtime object graph. This will be 
 * particularly useful in saving time to reconstruct the object (like sample/mock response object) during Unit Test 
 * run, rather performing the marshaling of the object from, say for example, XML/any other file. 
 * Generated builder class will have the supplied package name and class name. These are optional.
 * 
 * The class of <code>Parser</code> and <code>CodeGenerator</code> are the main components that do the complete 
 * parsing and generation of the source code for the given object.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ObjectCodeBuilder {

    private ImportStatementHelper importStmtHelper = new ImportStatementHelper();
    
    private Parser parser;
    
    public ObjectCodeBuilder(Object objectToBeProcessed) {
        if (objectToBeProcessed == null) {
            throw new NullPointerException("Given input is null");
        }
        parser = new ObjectParser(objectToBeProcessed, importStmtHelper);
    }

    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This method will parse the given object and return the builder source code.
     * 
     * @return String , the builder object source code
     * </pre>
     */
    public String buildObjectCode() {
        return buildObjectCode(null, null);
    }
    
    /**
     * 
     * <pre>
     * <b>Description : </b>
     * This method will parse the given object and return the builder source code.
     * The generated builder code will have the given package name and class name.
     * 
     * @param packageName , custom package name; may be null
     * @param customClassName , custom class name for generated builder class; may be null
     * @return String , the builder object source code
     * </pre>
     */
	public String buildObjectCode(String packageName, String customClassName) {
		ObjectTypeInfoHolder objectTypeInfoHolder = parser.parse();
		if (objectTypeInfoHolder != null) {
	        Set<String> importedClassNames = importStmtHelper.getMostUsedQualifierClassNames();
	        CodeGenerator generator = new CodeGenerator(importedClassNames, objectTypeInfoHolder, packageName, 
	            customClassName);
	        String instructions = generator.generateCode();
	        return instructions;
		} else {
            AppLoggerUtil.println("Invalid Input. Data Object only allowed.");
            return null;
		}
	}
	
}
