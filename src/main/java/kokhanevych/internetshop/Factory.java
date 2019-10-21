package kokhanevych.internetshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.dao.OrderDao;
import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.dao.hibernate.BucketDaoHibernateImpl;
import kokhanevych.internetshop.dao.hibernate.ItemDaoHibernateImpl;
import kokhanevych.internetshop.dao.hibernate.OrderDaoHibernateImpl;
import kokhanevych.internetshop.dao.hibernate.RoleDaoHibernateImpl;
import kokhanevych.internetshop.dao.hibernate.UserDaoHibernateImpl;
import kokhanevych.internetshop.service.BucketService;
import kokhanevych.internetshop.service.ItemService;
import kokhanevych.internetshop.service.OrderService;
import kokhanevych.internetshop.service.RoleService;
import kokhanevych.internetshop.service.UserService;
import kokhanevych.internetshop.service.impl.BucketServiceImpl;
import kokhanevych.internetshop.service.impl.ItemServiceImpl;
import kokhanevych.internetshop.service.impl.OrderServiceImpl;
import kokhanevych.internetshop.service.impl.RoleServiceImpl;
import kokhanevych.internetshop.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/internetshop?"
                            + "user=root&password=password111&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can’t establish connection to our DB ", e);
        }
    }

    private static UserDao userDaoInstance;
    private static BucketDao bucketDaoInstance;
    private static ItemDao itemDaoInstance;
    private static OrderDao orderDaoInstance;
    private static RoleDao roleDaoInstance;

    private static UserService userServiceInstance;
    private static BucketService bucketServiceInstance;
    private static ItemService itemServiceInstance;
    private static OrderService orderServiceInstance;
    private static RoleService roleServiceInstance;

    private Factory() {

    }

    public static RoleDao getRoleDaoInstance() {
        if (roleDaoInstance == null) {
            roleDaoInstance = new RoleDaoHibernateImpl();
        }
        return roleDaoInstance;
    }

    public static UserDao getUserDaoInstance() {
        if (userDaoInstance == null) {
            userDaoInstance = new UserDaoHibernateImpl();
        }
        return userDaoInstance;
    }

    public static BucketDao getBucketDaoInstance() {
        if (bucketDaoInstance == null) {
            bucketDaoInstance = new BucketDaoHibernateImpl();
        }
        return bucketDaoInstance;
    }

    public static ItemDao getItemDaoInstance() {
        if (itemDaoInstance == null) {
            itemDaoInstance = new ItemDaoHibernateImpl();
        }
        return itemDaoInstance;
    }

    public static OrderDao getOrderDaoInstance() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoHibernateImpl();
        }
        return orderDaoInstance;
    }

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }

    public static BucketService getBucketServiceInstance() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }

    public static ItemService getItemServiceInstance() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static OrderService getOrderServiceInstance() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static RoleService getRoleServiceInstance() {
        if (roleServiceInstance == null) {
            roleServiceInstance = new RoleServiceImpl();
        }
        return roleServiceInstance;
    }
}
