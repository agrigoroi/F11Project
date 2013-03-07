JFLAGS = -classpath .:./GUI/ 
JC = javac
JR = java
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		GUI/LoginGUI.java \
		GUI/MainGUI.java \
		GUI/ConfirmationGUI.java \
		GUI/RequestHolidayGUI.java \
		GUI/Window.java \
		GUI/HolidayGUI.java \
		GUI/WelcomeGUI.java \
		BusInfo.java \
		BusStopInfo.java \
		Driver.java \
		DriverInfo.java \
		InvalidQueryException.java \
		Request.java \
		RequestInfo.java \
		TimetableInfo.java \
		database.java \


default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
		$(RM) ./GUI/*.class