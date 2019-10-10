package mate.academy.internetshop.dao.hibernate;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HashUtil;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoHibernateImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoHibernateImpl.class);

    @Inject
    private static BucketDao bucketDao;

    @Override
    public User create(User user) {
        Long userId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            byte[] salt = HashUtil.getSalt();
            user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
            user.setSalt(salt);
            userId = (Long) session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        user.setId(userId);
        Bucket bucket = new Bucket(user);
        //bucket.setUser(user);
        bucketDao.create(bucket);
        return user;
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return user;
        }
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update user " + user, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = get(id);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete user " + user, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where login=:login");
            query.setParameter("login", login);
            User user = (User) query.uniqueResult();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Optional<User> getByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from User where token=:token");
            query.setParameter("token", token);
            List<User> list = query.list();
            return list.stream().findFirst();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createCriteria(User.class).list();
        }
    }
}
