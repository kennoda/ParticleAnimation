JC=javac
.SUFFIXES: .java .class
SOURCES=Animation.java
OBJECTS=$(SOURCES:.java=.class)

default: $(OBJECTS)

.java.class:
	$(JC) $*.java

clean:	
	rm -f *.class
	touch *

handin: clean
	handin cmsc280 lab3 ../lab3

