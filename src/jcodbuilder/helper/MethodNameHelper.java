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
package jcodbuilder.helper;

import static jcodbuilder.common.AppConstants.TXT_NO_SPACE;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <b>Description : </b>
 * This serves as a method name generator.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class MethodNameHelper {

    private Map<String, Integer> methodNameMap = null;
    
    public MethodNameHelper() {
        methodNameMap = new HashMap<String, Integer>();
    }
    
    public String getMethodName(MethodNameType methodNameFor, String givenValue) {
        if (givenValue == null) {
            return null;
        }
        String methodPrefix = TXT_NO_SPACE;
        if (MethodNameType.METHOD_NAME_FOR_OBJECT.equals(methodNameFor)) {
            methodPrefix = MethodNameType.METHOD_NAME_FOR_OBJECT.getMethodNamePrefix();
        } else if (MethodNameType.METHOD_NAME_FOR_ARRAY.equals(methodNameFor)) {
            methodPrefix = MethodNameType.METHOD_NAME_FOR_ARRAY.getMethodNamePrefix();
        } else if (MethodNameType.METHOD_NAME_FOR_COLLECTION.equals(methodNameFor)) {
            methodPrefix = MethodNameType.METHOD_NAME_FOR_COLLECTION.getMethodNamePrefix();
        }
        String methodName = null;
        String lookupName = methodPrefix + givenValue;
        Integer count = methodNameMap.get(lookupName);
        if (count == null) {
            methodNameMap.put(lookupName, 0);
            methodName = lookupName;
        } else {
            count++;
            methodNameMap.put(lookupName, count);
            methodName = lookupName + count;
        }
        return methodName;
    }
    
    public enum MethodNameType {
        METHOD_NAME_FOR_OBJECT("create"),
        
        METHOD_NAME_FOR_ARRAY("createArray"),
        
        METHOD_NAME_FOR_COLLECTION("createColl");
        
        private String methodNamePrefix;
        
        private MethodNameType(String methodNamePrefixParam) {
            this.methodNamePrefix = methodNamePrefixParam;
        }

        public String getMethodNamePrefix() {
            return methodNamePrefix;
        }

        public void setMethodNamePrefix(String methodNamePrefix) {
            this.methodNamePrefix = methodNamePrefix;
        }
     
    }
}
