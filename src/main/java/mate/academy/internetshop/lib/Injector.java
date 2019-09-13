package mate.academy.internetshop.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mate.academy.internetshop.Main;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class Injector {

    public static void injectServiceClasses() throws IllegalAccessException {
        List<Field> fieldsList = new ArrayList<>();
        Field [] userFields = UserServiceImpl.class.getDeclaredFields();
        fieldsList.addAll(Arrays.asList(userFields));
        Field [] bucketFields = BucketServiceImpl.class.getDeclaredFields();
        fieldsList.addAll(Arrays.asList(bucketFields));
        Field [] itemFields = ItemServiceImpl.class.getDeclaredFields();
        fieldsList.addAll(Arrays.asList(itemFields));
        Field [] orderFields = OrderServiceImpl.class.getDeclaredFields();
        fieldsList.addAll(Arrays.asList(orderFields));
        for (Field field : fieldsList) {
            if (field.getDeclaredAnnotation(Inject.class) != null) {
                field.setAccessible(true);
                field.set(null, AnnotatedClassMap.getImplementation(field.getType()));
            }
        }
    }

    public static void injectMainClass() throws IllegalAccessException {
        Class<Main> mainClass = Main.class;
        Field [] mainClassFields = mainClass.getDeclaredFields();
        for (Field field : mainClassFields) {
            if (field.getDeclaredAnnotation(Inject.class) != null) {
                field.setAccessible(true);
                field.set(null, AnnotatedClassMap.getImplementation(field.getType()));
            }
        }
    }
}


