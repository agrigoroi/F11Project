JCFLAGS = -classpath .:./GUI:./GUI/jcalendar-1.4.jar
JRFLAGS = -classpath .:..:./jcalendar-1.4.jar:../mysql-connector-java-5.1.12-bin.jar
JC = javac
JR = java
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JCFLAGS) $*.java

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

run: default
		cd ./GUI; $(JR) $(JRFLAGS) MainGUI

recompile: clean default

clean:
		$(RM) *.class
		$(RM) ./GUI/*.class
