package home.lesson6.utils;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Stack;

public class Printer {
    private Class<?> currentClass;

    public Printer(String className) throws ClassNotFoundException {
        currentClass = Class.forName(className);
    }

    public void print() {
        Class<?> currClass = currentClass;
        Stack<Class<?>> classes = new Stack<>();
        while (currClass != null) {
            classes.add(currClass);
            currClass = currClass.getSuperclass();
        }
        int i = 0;
        int len = classes.size();
        for (Class<?> aClass : classes) {
            System.out.println("Class: " + aClass.getSimpleName());
            printMembers(aClass.getDeclaredFields());
            printMembers(aClass.getDeclaredConstructors());
            printMembers(aClass.getDeclaredMethods());
            if (i++ < len - 1) {
                System.out.println("\n^");
            }
        }
    }

    public void printGetters() {
        Method[] declaredMethods = currentClass.getDeclaredMethods();
        Field[] fields = currentClass.getDeclaredFields();
        if (declaredMethods == null || fields == null) {
            return;
        }
        System.out.println("Getters: ");
        HashMap<String, Method> methodsMap = getMethodsMap(declaredMethods);
        Method[] resultMethods = new Method[declaredMethods.length];
        int i = 0;
        for (Field field : fields) {
            String methodName = getMethodName(field.getName());
            if (methodsMap.containsKey(methodName)) {
                resultMethods[i++] = methodsMap.get(methodName);
            }
        }
        printMembers(resultMethods);
    }

    public boolean checkStringFinal() throws Exception {
        boolean result = true;
        Field[] fields = currentClass.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return  false;
        }
        Constructor<?> constructor = currentClass.getConstructor();
        Object instance = constructor.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers()) && getMemberType(field).equals("String")) {
                if (!field.getName().equals(field.get(instance))) {
                    result = false;
                } else {
                    System.out.println(field.getName());
                }
            }
        }

        return result;
    }

    private HashMap<String, Method> getMethodsMap(Method[] declaredMethods) {
        HashMap<String, Method> methodsMap = new HashMap<>();
        if (declaredMethods != null && declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {
                methodsMap.put(declaredMethod.getName(), declaredMethod);
            }
        }
        return methodsMap;
    }

    private String getMethodName(String name) {
        return "get" +
                name.substring(0, 1).toUpperCase() +
                name.substring(1);
    }

    private static void printMembers(Member... members) {
        if (members == null || members.length == 0) {
            return;
        }
        System.out.println(members[0].getClass().getSimpleName() + "s: ");
        for (Member member : members) {
            if (member == null) {
                continue;
            }
            System.out.println(mod(member.getModifiers()) +
                            " " + getMemberType(member) +
                            " " + member.getName()
            );
            if (member instanceof Executable) {
                System.out.println(printParameters(((Executable) member).getParameterTypes()));
            }
        }
        System.out.println("\n------------");
    }

    private static String getMemberType(Member member) {
        String type = "";
        if (member instanceof Field) {
            type = ((Field) member).getType().getSimpleName();
        } else if (member instanceof Method) {
            type = ((Method) member).getReturnType().getSimpleName();
        }
        return type;
    }

    public static String printParameters(Class<?>[] parameterTypes) {
        StringBuilder result = new StringBuilder("Parameters: ");
        if (parameterTypes != null && parameterTypes.length > 0) {
            boolean flag = false;
            for (Class<?> parameter : parameterTypes) {
                if (flag) {
                    result.append(", ");
                } else {
                    flag = true;
                }
                result.append(parameter.getSimpleName());
            }
        } else {
            result.append("empty");
        }
        result.append("\n");
        return result.toString();
    }

    private static String mod(int modifiers) {
        return Modifier.toString(modifiers);
    }
}
