My model is implemented in that so that any exception that is thrown while 
building or updating an automobile while trigger the user input and it makes
sure that the input given is in the right type (text for Strings, numbers for float).

The AutoException object also has a map for holding any additional information the 
exception handler needs to know about the exception that was thrown (i.e. the value of the variable
that caused the exception).

Also, because the user input needs to be given back to where the exception is caught
the class has another map with the information that the fix came out with.