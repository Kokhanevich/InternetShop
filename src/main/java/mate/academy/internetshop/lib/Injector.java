package mate.academy.internetshop.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.Main;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class Injector {

    public static void inject() throws IllegalAccessException {
        List<Class> classes = new ArrayList<>();
        classes.add(ItemServiceImpl.class);
        classes.add(BucketServiceImpl.class);
        classes.add(UserServiceImpl.class);
        classes.add(OrderServiceImpl.class);
        classes.add(Main.class);
        for (Class clazz : classes) {
            Field [] fieldsList = clazz.getDeclaredFields();
            for (Field field : fieldsList) {
                if (field.getDeclaredAnnotation(Inject.class) != null) {
                    field.setAccessible(true);
                    field.set(null, AnnotatedClassMap.getImplementation(field.getType()));
                }
            }
        }
    }
}
