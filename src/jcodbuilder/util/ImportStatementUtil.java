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
package jcodbuilder.util;

import static jcodbuilder.common.AppConstants.SYMBOL_DOT;
import static jcodbuilder.common.PropertiesBasedAppConstants.CLASS_SEPARATOR;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * <pre>
 * <b>Description : </b>
 * Util class for maintaining and extracting the class details required for generating the <code>import</code> statement.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class ImportStatementUtil {

    public static Set<String> getMostUsedQualifierClassNamesForImportStmt(
        Map<String, Map<String, Integer>> qualifiedClassCountMap) {
        Set<String> selectedQualifiedClasses = new TreeSet<String>();
        if (qualifiedClassCountMap == null || qualifiedClassCountMap.size() == 0) {
            return selectedQualifiedClasses;
        }

        String highestQualifierName;
        int highestQualifierCount;
        Set<Entry<String, Map<String, Integer>>> classEntries = qualifiedClassCountMap.entrySet();
        //Main-Key
        String className = null;
        //Main-Value
        Set<Entry<String, Integer>> qualifierCountEntries = null;
        //Sub-Key
        String qualifier = null;
        //Sub-Value
        Integer qualifierCount = null;
        
        for (Entry<String, Map<String, Integer>> classEntry : classEntries) {
            if (classEntry != null) {
                className = classEntry.getKey();
                qualifierCountEntries = classEntry.getValue().entrySet();
                
                highestQualifierName = null;
                highestQualifierCount = 0;
                for (Entry<String, Integer> qualifierCountEntry : qualifierCountEntries) {
                    if (qualifierCountEntry != null) {
                        qualifier = qualifierCountEntry.getKey();
                        qualifierCount = qualifierCountEntry.getValue();
                        if (qualifier == null) {
                            highestQualifierName = null;
                            break;
                        }
                        if (qualifierCount > highestQualifierCount) {
                            highestQualifierCount = qualifierCount;
                            highestQualifierName = qualifier;
                        }
                    }
                }
                
                if (highestQualifierName != null) {
                    selectedQualifiedClasses.add(highestQualifierName + SYMBOL_DOT + className);
                }
            }
        }
        
        return selectedQualifiedClasses;
    }
    
    public static String[] extractQualifierAndClassName(Class classType) {
        String fullyQualifiedClassName = classType.getCanonicalName();
        String[] classDetails = new String[2];
        if (fullyQualifiedClassName != null) {
            int indexOfLastDot = fullyQualifiedClassName.lastIndexOf(CLASS_SEPARATOR);
            if (indexOfLastDot == -1) {
                classDetails[1] = fullyQualifiedClassName;
            } else {
                classDetails[0] = fullyQualifiedClassName.substring(0, indexOfLastDot);
                classDetails[1] = fullyQualifiedClassName.substring(indexOfLastDot + 1);
            }
        }
        return classDetails;
    }

    public static String getQualifiedClassName(String qualifier, String className, Set<String> importedClassNames) {
        if (qualifier == null) {
            return className;
        }
        String qualifiedClassName = qualifier + CLASS_SEPARATOR + className;
        if (importedClassNames.contains(qualifiedClassName)) {
            return className;
        } else {
            return qualifiedClassName;
        }
    }
    
}
