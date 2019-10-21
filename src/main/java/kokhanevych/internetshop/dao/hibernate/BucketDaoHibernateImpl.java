package kokhanevych.internetshop.dao.hibernate;

import java.util.List;

import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class BucketDaoHibernateImpl implements BucketDao {
    private static Logger logger = Logger.getLogger(BucketDaoHibernateImpl.class);

    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        Long bucketId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucketId = (Long) session.save(bucket);
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
        bucket.setId(bucketId);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = session.get(Bucket.class, bucketId);
            return bucket;
        }
    }

    @Override
    public Bucket update(Bucket bucket) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public void delete(Long bucketId) {
        Bucket bucket = get(bucketId);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete bucket " + bucket, e);
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
    public Bucket addItem(Long bucketId, Long itemId) {
        Session session = null;
        Bucket bucket = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Item item = itemDao.get(itemId);
            bucket = get(bucketId);
            bucket.getItems().add(item);
            bucket = update(bucket);
        } catch (Exception e) {
            logger.error("Can’t add item to bucket", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return get(bucketId).getItems();
    }

    @Override
    public void deleteItem(Item item, Long bucketId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Bucket bucket = get(bucketId);
            Item removedItem = bucket.getItems()
                    .stream()
                    .filter(i -> i.getId().equals(item.getId()))
                    .findFirst().get();
            bucket.getItems().remove(removedItem);
            update(bucket);
        } catch (Exception e) {
            logger.error("Can’t delete item " + item, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void clear(Long bucketId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Bucket bucket = get(bucketId);
            bucket.getItems().clear();
            update(bucket);
        } catch (Exception e) {
            logger.error("Cant clear bucket", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
