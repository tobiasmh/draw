
##Business requirements assumptions
* The input dimensions for the example canvas exclude the top and bottom borders but include the left and right borders. 
The application has been implemented using this logic. 

* The example provided for the rectangle had origin points that did not appear to be either zero or one indexed against the canvas.
The implementation has been done using an expectation for a zero indexed origin point for the top left and bottom right corners.

* When a fill takes place on an existing fill area the new fill will overwrite the existing fill.

* When a point on a line or rectangle is drawn on top of an existing line or rectangle this will overlap the existing drawing.

##Architecture notes
* Spring has been used to ease dependency injection and simplify the application. The application has been written in a way that Spring can be easily removed. 

* Tests are named using Roys standard http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
[UnitOfWork_StateUnderTest_ExpectedBehavior] - i.e. Sum_NegativeNumberAs1stParam_ExceptionThrown()

* The controller classes are responsible for validation and will return appropriate validation messages to the user.
The service classes use a defensive runtime exception strategy where inputs are once again checked to prevent inconsistent behaviour, 
and to also protect against future consumers of the class missing required validation criteria.

* Small classes have been used to help with readability (especially the readability of test classes), 
to assist with ensuring that classes only have a single responsibility and to help improve the cohesiveness of the implementation.

* ZohhakRunner has been used for parameter input injection on certain tests to improve readability.

* The end to end integration test is designed to be run explicity.

* Abstractions and wrappers have been used for functionality like the console output, canvas model and canvas points.
 This is to facilitate future extensibility without requiring extensive refactoring.


##Potential future improvements
* Extract hardcoded strings to a localization file.
* Modify service signatures to use structures for coordinate parameters.

##Run instructions
###Prerequisites
* JRE 1.8+

###Steps
```
git clone https://github.com/tobiasmh/draw.git
java -jar dist/draw-1.0.jar
```
 
##Build instructions
###Prerequisites
* JDK 1.8+
* Maven 3.3+

###Steps
```
git clone https://github.com/tobiasmh/draw.git
mvn package
```