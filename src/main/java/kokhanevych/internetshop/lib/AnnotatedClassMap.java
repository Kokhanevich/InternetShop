package kokhanevych.internetshop.lib;

import java.util.HashMap;
import java.util.Map;

import kokhanevych.internetshop.Factory;
import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.dao.OrderDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.service.BucketService;
import kokhanevych.internetshop.service.ItemService;
import kokhanevych.internetshop.service.OrderService;
import kokhanevych.internetshop.service.RoleService;
import kokhanevych.internetshop.service.UserService;

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
        classMap.put(RoleService.class, Factory.getRoleServiceInstance());
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
