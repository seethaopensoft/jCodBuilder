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
package jcodbuilder.common;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 * <b>Description : </b>
 * All constants used in the application are defined here.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public interface AppConstants {
    
    // Texts
    String TXT_NO_SPACE = "";
    
    String TXT_SINGLE_SPACE = " ";
    
    String TXT_NEW_LINE = System.lineSeparator();
    
    String TXT_FORMAT_FOR_LONG = "L";
    
    String TXT_FORMAT_FOR_FLOAT = "F";
    
    
    // Keywords    
    String KEYWORD_MODIFIER_PUBLIC = "public";
    
    String KEYWORD_MODIFIER_PUBLIC_WITH_SPACE = KEYWORD_MODIFIER_PUBLIC + TXT_SINGLE_SPACE;
    
    String KEYWORD_MODIFIER_PRIVATE = "private";
    
    String KEYWORD_MODIFIER_PRIVATE_WITH_SPACE = KEYWORD_MODIFIER_PRIVATE + TXT_SINGLE_SPACE;
    
    String KEYWORD_MODIFIER_STATIC = "static";
    
    String KEYWORD_MODIFIER_STATIC_WITH_SPACE = KEYWORD_MODIFIER_STATIC + TXT_SINGLE_SPACE;
    
    String KEYWORD_CLASS_WITH_SPACE = "class" + TXT_SINGLE_SPACE;
    
    String KEYWORD_IMPORT_WITH_SPACE = "import" + TXT_SINGLE_SPACE;
    
    String KEYWORD_PACKAGE_WITH_SPACE = "package" + TXT_SINGLE_SPACE;
    
    String KEYWORD_NEW_WITH_SPACE = "new" + TXT_SINGLE_SPACE;
    
    String KEYWORD_RETURN_WITH_SPACE = "return" + TXT_SINGLE_SPACE;
    
    
    
    // Symbols
    String SYMBOL_DOUBLE_QUOTE = "\"";
    
    String SYMBOL_SINGLE_QUOTE = "'";
    
    String SYMBOL_DOT = ".";
    
    String SYMBOL_COMMA = ",";
    
    String SYMBOL_COMMA_WITH_SPACE = SYMBOL_COMMA + TXT_SINGLE_SPACE;
    
    String SYMBOL_CURLY_BRACES_OPEN = "{";
    
    String SYMBOL_CURLY_BRACES_CLOSE = "}";
    
    String SYMBOL_PARENTHESIS_OPEN= "(";
    
    String SYMBOL_PARENTHESIS_CLOSE = ")";
    
    String SYMBOL_PARENTHESIS_OPEN_CLOSE = "()";
    
    String SYMBOL_SQUARE_BRACKET_OPEN = "[";
    
    String SYMBOL_SQUARE_BRACKET_CLOSE = "]";
    
    String SYMBOL_SQUARE_BRACKET_OPEN_CLOSE = "[]";
    
    String SYMBOL_SEMI_COLON = ";";
    
    String SYMBOL_COLON = ":";

    String SYMBOL_COLON_WITH_SPACE = TXT_SINGLE_SPACE + SYMBOL_COLON + TXT_SINGLE_SPACE;

    String SYMBOL_EQUALS = "=";

    String SYMBOL_EQUALS_WITH_SPACE = TXT_SINGLE_SPACE + SYMBOL_EQUALS + TXT_SINGLE_SPACE;
    
    
    // Miscellaneous
    String STR_UNKNOWN = "Unknown";
    
    String STR_BUILDER = "Builder";

	String STR_BUILD_OBJECT = "buildObject";

    String STR_LOCAL_VARIABLE_NAME_FOR_OBJECT = "obj";
    
    String STR_LOCAL_VARIABLE_NAME_FOR_ARRAY_OBJECT = "arrayObj";
    
    String STR_LOCAL_VARIABLE_NAME_FOR_COLLECTION_OBJECT = "collObj";
    
    String STR_PARAMETER_NAME_FOR_ENCLOSING_OBJECT = "enclosingObj";
    
    String STR_TYPE1_COLLECTION_METHOD_NAME = "add";
    
    String STR_TYPE2_COLLECTION_METHOD_NAME = "put";
    
    String STR_TYPE2_STRING_METHOD_NAME = "append";
    
    String STR_USE_GENERIC_METHOD_NAME_FOR_ARRAY_OR_COLLECTION_ITEM = "#ARRAY_OR_COLLECTION_ITEM#";
    
    String STR_GENERIC_METHOD_NAME_FOR_SIMPLE_TYPE = "SimpleType";
    
    String STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE1 = "Type1";

    String STR_GENERIC_METHOD_NAME_FOR_COLLECTION_TYPE2 = "Type2";
    
    String STR_PROPERTY_NAME_CLASS = "class";

    Class CLASS_FOR_TYPE1_COLLECTION = Collection.class;
    
    Class CLASS_FOR_TYPE2_COLLECTION = Map.class;
    
    String FILE_SEPARATOR = System.getProperty("file.separator");

    String PROP_FILE_NAME_APP_PROPS = "AppProps.prop";

    String PROP_FILE_NAME_HANDLER_MAPPER_EXACT = "HandlerMapperExact.prop";

    String PROP_FILE_NAME_HANDLER_MAPPER_INSTANCEOF = "HandlerMapperInstanceof.prop";
 
    int CHAR_NUMBER_START = 48;
    
    int CHAR_NUMBER_END = 57;
    
    int CHAR_ALPHABET_UPPER_START = 65;
    
    int CHAR_ALPHABET_UPPER_END = 90;
    
    int CHAR_ALPHABET_LOWER_START = 97;
    
    int CHAR_ALPHABET_LOWER_END = 122;
    
    String STRING_HANDLING_MODE = "STRING_HANDLING_MODE";
    
    String STRING_HANDLING_MODE_USUAL = "USUAL";
    
    String STRING_HANDLING_MODE_CHAR = "CHAR";
    
    int SLIZING_SIZE_FOR_STRING_SPLIT_INTO_MULTI_LINE = 120;
    
    int DEFAULT_INDENT_LEVEL_FOR_STRING_SPLIT_INTO_MULTI_LINE = 3;

    
}
