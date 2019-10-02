package mate.academy.internetshop.lib;

import java.util.HashMap;
import java.util.Map;

import mate.academy.internetshop.Factory;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

class AnnotatedClassMap {
    private static final Map<Class,Object> classMap = new HashMap<>();

    static {
        classMap.put(UserDao.class, Factory.getUserDaoInstance());
        classMap.put(BucketDao.class, Factory.getBucketDaoInstance());
        classMap.put(ItemDao.class, Factory.getItemDaoInstance());
        classMap.put(OrderDao.class, Factory.getOrderDaoInstance());
        classMap.put(RoleDao.class, Factory.getRoleDaoInstance());
        classMap.put(UserService.class, Factory.getUserServiceInstance());
        classMap.put(BucketService.class, Factory.getBucketServiceInstance());
        classMap.put(ItemService.class, Factory.getItemServiceInstance());
        classMap.put(OrderService.class, Factory.getOrderServiceInstance());
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
