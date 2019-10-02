package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can’n find bucket with id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).equals(bucket)) {
                Storage.buckets.set(i, bucket);
                return bucket;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void delete(Long bucketId) {
        Storage.buckets.removeIf(item -> item.getId().equals(bucketId));
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        return null;
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return null;
    }

    @Override
    public void deleteItem(Item item, Long bucketId) {

    }

    @Override
    public void clear(Long bucketId) {

    }

}
