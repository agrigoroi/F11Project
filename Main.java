/*
 * A very simple application illustrating how to use the interface.
 * Prints the names of all the drivers in the database.
 * @author John Sargeant
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        database.openBusDatabase();
         String password = DriverInfo.getPass(2012);
	 System.out.println(password);
/*        String[] driverNames = new String [driverIDs.length];
        for (int i=0; i<driverIDs.length; i++)
            System.out.println(DriverInfo.getName(driverIDs[i]));*/
    }

}
