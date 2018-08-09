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

import jcodbuilder.dos.ClassInfoHolder;
import jcodbuilder.dos.ObjectTypeInfoHolder;
import jcodbuilder.helper.MethodNameHelper;
import jcodbuilder.writer.JavaFileWriter;
import static jcodbuilder.common.AppConstants.*;

/**
 * <pre>
 * <b>Description : </b>
 * This class is responsible for generating the source code for Object builder.  
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class CodeGenerator {
    
    private Set<String> importedClassNames;

    private ObjectTypeInfoHolder objectTypeInfoHolder;
    
    private String packageName;

    private String customClassName;
    
    public CodeGenerator(Set<String> importedClassNamesParam, ObjectTypeInfoHolder objectTypeInfoHolderParam, 
        String packageNameParam, String customClassNameParam) {
        this.importedClassNames = importedClassNamesParam;
        this.objectTypeInfoHolder = objectTypeInfoHolderParam;
        this.packageName = packageNameParam;
        this.customClassName = customClassNameParam;
    }

    public String generateCode() {
    	ClassInfoHolder classInfoHolder = new ClassInfoHolder();
    	classInfoHolder.setQualifier(packageName);
    	if (customClassName != null && customClassName.trim().length() > 0) {
            classInfoHolder.setClassName(customClassName.trim() + STR_BUILDER);
    	} else {
    	    classInfoHolder.setClassName(objectTypeInfoHolder.getClassName() + STR_BUILDER);
    	}
    	classInfoHolder.setRootObjectInfoHolder(objectTypeInfoHolder);
    	objectTypeInfoHolder.setAllocatedMethodName(STR_BUILD_OBJECT);
    	MethodNameHelper methodNameHelper = new MethodNameHelper();
    	JavaFileWriter javaFileWriter = new JavaFileWriter(methodNameHelper, importedClassNames, classInfoHolder);
    	javaFileWriter.process();
    	return javaFileWriter.getInstruction();
    }
    
}
