package mate.academy.internetshop.dao.hibernate;

import java.util.List;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.util.HibernateUtil;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            bucketId = (Long) session.save(bucket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return bucket;
    }

    @Override
    public void delete(Long bucketId) {
        Bucket bucket = get(bucketId);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Item item = itemDao.get(itemId);
            Bucket bucket = get(bucketId);
            bucket.getItems().add(item);
            return update(bucket);
        }
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return get(bucketId).getItems();
    }

    @Override
    public void deleteItem(Item item, Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId);
            Item removedItem = bucket.getItems()
                    .stream()
                    .filter(i -> i.getId().equals(item.getId()))
                    .findFirst().get();
            bucket.getItems().remove(removedItem);
            update(bucket);
        } catch (Exception e) {
            logger.error("Can’t delete item " + item, e);
        }
    }

    @Override
    public void clear(Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId);
            bucket.getItems().clear();
        } catch (Exception e) {
            logger.error("Cant clear bucket", e);
        }
    }
}
