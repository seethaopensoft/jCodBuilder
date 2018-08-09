This library does the job of taking the runtime object as an input and generating the java source code in order to 
reconstruct them. This is more of like marshalling the Java object in a source code form. The generated class file 
will act like object builder for the given runtime object.

This may be useful in saving time when we need to deal with unmarshalling the mock response from XML or other file 
during Unit Test run. 

If any specific type should be handled differently, then the handler class of type CustomObjectTypeHandler should be 
created and information about this should be detailed in one of the below file, as applicable.
	1) HandlerMapperExact.prop - use this when exact match to be done for handling the type.
	2) HandlerMapperInstanceof.prop - use this when the handler should be chosen on the basis of "instanceof" type check.

If the String type that is being handled contains non human-readable characters, then that can be handled safely by 
passing the system property (key as STRING_HANDLING_MODE and value as USUAL and CHAR).

This library has no dependency on other libraries.

Usage:
Just pass the runtime object to the class ObjectCodeBuilder as follow:

ObjectCodeBuilder builder = new ObjectCodeBuilder(object);
String data = builder.buildObjectCode("com.somepackage.pack1", "SomeBuilderClassName");
System.out.println(data);

The generated source code will have the method called "buildObject", which can be used to reconstruct the supplied object.

