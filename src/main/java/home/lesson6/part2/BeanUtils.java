package home.lesson6.part2;

import home.lesson6.utils.Test;
import home.lesson6.utils.Test2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Home work: lesson6
 */
public class BeanUtils {
    public static void main(String[] args) throws Exception{
        Test2 to = new Test2();
        Test from = new Test("Ivan", 36, 52255);
        assign(to, from);

        System.out.println(to);
    }

    public static void assign(Object to, Object from) throws Exception {
        Method[] fromMethods = from.getClass().getMethods();
        Method[] toMethods = to.getClass().getMethods();
        Field[] toFields = to.getClass().getDeclaredFields();
        if (fromMethods == null || toFields == null || toMethods == null) {
            return;
        }

        HashMap<String, Method> getMethodsMap = getMethodsMap(fromMethods);
        HashMap<String, Method> setMethodsMap = getMethodsMap(toMethods);

        for (Field field : toFields) {
            String methodName = getMethodName(field.getName());
            if (getMethodsMap.containsKey("get" + methodName) && setMethodsMap.containsKey("set" + methodName)) {
                Method getter = getMethodsMap.get("get" + methodName);
                if (getter.getReturnType().equals(field.getType()) ||
                        field.getType().getSuperclass().equals(getter.getReturnType())) {
                    setMethodsMap.get("set" + methodName).invoke(to, getter.invoke(from));
                }
            }
        }
    }

    private static HashMap<String, Method> getMethodsMap(Method[] declaredMethods) {
        HashMap<String, Method> methodsMap = new HashMap<>();
        if (declaredMethods != null && declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {
                methodsMap.put(declaredMethod.getName(), declaredMethod);
            }
        }
        return methodsMap;
    }

    private static String getMethodName(String name) {
        return name.substring(0, 1).toUpperCase() +
                name.substring(1);
    }
}
