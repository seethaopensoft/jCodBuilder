If you are looking a library to help you build the mock object (say, service response) without unmarshalling it from XML/JSON/etc during test run in order to save time, then this can help you out. This takes the runtime object (basically after unmarshalling from XML/etc) as an input and generates the source code to rebuild the same object that can be used during test run for faster execution. The generated code will act as a builder class to build this specific object.

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

