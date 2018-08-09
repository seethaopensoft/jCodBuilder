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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jcodbuilder.util.ImportStatementUtil;

/**
 * <pre>
 * <b>Description : </b>
 * This holds the details about the classes used and the usage count of them in order to generate the 
 * <code>import</code> statement for the most used class names. 
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ImportStatementHelper {

    private Map<String, Map<String, Integer>> qualifiedClassCountMap = null;
    
    public ImportStatementHelper() {
        qualifiedClassCountMap = new TreeMap<String, Map<String, Integer>>();
    }
    
    public void addQualifiedClassName(String qualifier, String className) {
        Map<String, Integer> classEntry = qualifiedClassCountMap.get(className);
        
        if (classEntry == null) {
            classEntry = new HashMap<String, Integer>();
            classEntry.put(qualifier, 1);
            qualifiedClassCountMap.put(className, classEntry);
        } else {
            Integer classCount = classEntry.get(qualifier);
            if (classCount == null) {
                classCount = 1;
            } else {
                classCount++;
            }
            classEntry.put(qualifier, classCount);
        }
    }
    
    public Set<String> getMostUsedQualifierClassNames() {
        return ImportStatementUtil.getMostUsedQualifierClassNamesForImportStmt(qualifiedClassCountMap);
    }
    
}
