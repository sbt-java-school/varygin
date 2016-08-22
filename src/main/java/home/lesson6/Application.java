package home.lesson6;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Stack;

/**
 * Print all fields, constructors, methods and superclasses by classname in first argument of program
 */
public class Application {

    /**
     * First argument of program must contain full path to existing class
     * For example:
     *              home.lesson6.Test
     *              java.lang.String
     *              home.util.Stack
     * @param args
     */
    public static void main(String[] args) {
        try {
            final Class<?> aClass = Class.forName(args[0]);
            printFields(aClass.getDeclaredFields());
            printConstructors(aClass.getDeclaredConstructors());
            printMethods(aClass.getDeclaredMethods());
            printSuperClasses(aClass);
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + args[0] + " not found ");
        }
    }

    private static void printMethods(Method[] declaredMethods) {
        System.out.println("Methods: ");
        for (Method declaredMethod : declaredMethods) {
            String retType = declaredMethod.getReturnType().getSimpleName();
            final StringBuilder str = new StringBuilder();
            str.append(mod(declaredMethod.getModifiers()) + " ");
            str.append(retType + " ");
            str.append(declaredMethod.getName());
            System.out.println(str);
            printParameters(declaredMethod.getParameterTypes(), "without parameters");
            System.out.println("\n");
        }
    }

    private static void printFields(Field[] declaredFields) {
        System.out.println("Fields: ");
        for (Field declaredField : declaredFields) {
            final String type = declaredField.getType().getTypeName();
            System.out.println(mod(declaredField.getModifiers()) + " " + type + " " + declaredField.getName());
        }
        System.out.println();
    }

    private static String mod(int modifiers) {
        return Modifier.toString(modifiers);
    }

    private static void printConstructors(Constructor<?>[] constructors) {
        System.out.println("Constructors: ");
        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor: " + mod(constructor.getModifiers()) + " " + constructor.getName());
            printParameters(constructor.getParameterTypes(), "Default Constructor");
            System.out.println("\n");
        }
    }

    private static void printParameters(Class<?>[] parameterTypes, String defMess) {
        if (parameterTypes.length > 0) {
            System.out.print("Parameters: ");
            boolean flag = false;
            for (Class<?> parameter : parameterTypes) {
                System.out.print((!flag ? "" : ", ") + parameter.getSimpleName());
                flag = true;
            }
        } else {
            System.out.print(defMess);
        }
    }

    private static void printSuperClasses(Class<?> testClass) {
        System.out.println("Superclasses: ");
        Stack<Class<?>> classes = new Stack<>();
        while (testClass != null) {
            classes.add(testClass);
            testClass = testClass.getSuperclass();
        }
        int i = 0;
        int len = classes.size();
        for (Class<?> aClass : classes) {
            System.out.print(aClass.getSimpleName());
            if (i++ < len - 1) {
                System.out.println("\n^");
            }
        }
        System.out.println();
    }
}
