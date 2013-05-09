JCFLAGS = -classpath .:./GUI:./GUI/jcalendar-1.4.jar
JRFLAGS = -classpath .:..:./jcalendar-1.4.jar:../mysql-connector-java-5.1.12-bin.jar
JRFLAG2 = -classpath .:./GUI:./GUI/jcalendar-1.4.jar:./mysql-connector-java-5.1.12-bin.jar
JC = javac
JR = java
.SUFFIXES: .java .class
.java.class:
		$(JC) -Xlint:deprecation $(JCFLAGS) $*.java

CLASSES = \
		GUI/LoginGUI.java \
		GUI/MainGUI.java \
		GUI/ConfirmationGUI.java \
		GUI/RequestHolidayGUI.java \
		GUI/Window.java \
		GUI/HolidayGUI.java \
		GUI/WelcomeGUI.java \
		GUI/ControllerPanelGUI.java \
		GUI/ControllerPanelDelaysGUI.java \
		GUI/TimetableGUI.java \
		GUI/PassengerGUI.java \
		BusInfo.java \
		BusStopInfo.java \
		Bus.java \
		database.java \
		Driver.java \
		DriverInfo.java \
		InvalidQueryException.java \
		Request.java \
		RequestInfo.java \
		Route.java \
		TimetableInfo.java \
		database.java \
		Roster.java \
		Service.java \
		TimingPoint.java \
		BusTimetable.java \
		DriverTimetable.java \
		RosterInfo.java \
		TestRosterInfo.java \


default: classes

classes: $(CLASSES:.java=.class)

run: default
		cd ./GUI; $(JR) $(JRFLAGS) MainGUI
run2: default
		$(JR) $(JRFLAG2) Roster
testRosterInfo: default
		$(JR) $(JRFLAG2) TestRosterInfo

recompile: clean default

clean:
		$(RM) *.class
		$(RM) ./GUI/*.class
