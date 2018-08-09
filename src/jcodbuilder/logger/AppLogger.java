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
package jcodbuilder.logger;

import java.io.OutputStream;

/**
 * <pre>
 * <b>Description : </b>
 * This serves as a single point for printing the logs. Currently it supports the printing of the log to console and 
 * also to the stream, when supplied.
 * 
 * @author Seetharaman Radhakrishnan 2018-07-15
 * </pre>
 */
public class AppLogger {

    private static OutputStream outStream;
    
    public static void registerStream(OutputStream os) {
        outStream = os;
    }
    
    public static void log(String data) {
        writeToConsole(data);
        if (outStream != null) {
            writeToStream(data);
        }
    }
    
    private static void writeToConsole(String data) {
        System.out.print(data);
    }
    
    private static void writeToStream(String data) {
        try {
            outStream.write(data.getBytes());
        } catch (Exception e) {
            writeToConsole("Unable to write to given OutputStream");
            e.printStackTrace();
        }
    }
    
}
