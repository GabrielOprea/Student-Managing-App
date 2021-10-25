package email;

import annotations.EmailField;
import annotations.EmailText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import java.util.logging.Level;


public class EmailHandler {

    private static final Logger logger = Logger.getLogger(EmailHandler.class.getName());

    public static void method(Object obj) throws IllegalAccessException {
        Class aClass = obj.getClass();
        Annotation[] annotations = aClass.getAnnotations();

        String value = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof EmailText) {
                EmailText myAnnotation = (EmailText) annotation;
                value = myAnnotation.value();
            }
        }

        Field[] fields = aClass.getDeclaredFields();
        for(Field f : fields) {
            f.setAccessible(true);
            for(Annotation annotation : f.getDeclaredAnnotations()) {
                if(annotation instanceof EmailField) {
                    EmailField ef = (EmailField) annotation;
                    String declaredValueAsString;
                    Object declaredValue = f.get(obj);
                    if(declaredValue instanceof String) {
                        declaredValueAsString = (String) declaredValue;
                    } else {
                        declaredValueAsString = String.valueOf(declaredValue);
                    }
                    value = value.replaceFirst(ef.value(), declaredValueAsString);
                }
            }
        }
        logger.log(Level.INFO, value);
    }
}
