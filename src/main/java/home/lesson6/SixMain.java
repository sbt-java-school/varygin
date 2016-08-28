package home.lesson6;

/**
 * Print all fields, constructors, methods and superclasses by classname in first argument of program
 */
public class SixMain {

    /**
     * First argument of program must contain full path to existing class
     * For example:
     * home.lesson6.Test
     * java.lang.String
     * home.util.Stack
     *
     * @param args in first parameter must be name of the parsed class
     */
    public static void main(String[] args) throws Exception {
        try {
            Printer classPrinter = new Printer(args[0]);
            classPrinter.print();

            classPrinter.printGetters();

            if (classPrinter.checkStringFinal()) {
                System.out.println("All constants have value the same as it name.");
            } else {
                System.out.println("Not all constants.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + args[0] + " not found ");
        }
    }
}
