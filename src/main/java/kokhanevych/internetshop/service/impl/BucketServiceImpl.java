package kokhanevych.internetshop.service.impl;

import java.util.List;

import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.lib.Service;
import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        bucketDao.create(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        return bucketDao.get(bucketId);
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Long bucketId) {
        bucketDao.delete(bucketId);
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        return bucketDao.addItem(bucketId, itemId);
    }

    @Override
    public Bucket clear(Long bucketId) {
        Bucket bucket = bucketDao.get(bucketId);
        bucketDao.clear(bucketId);
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return bucketDao.getAllItems(bucketId);
    }

    @Override
    public void deleteItem(Item item, Long bucketId) {
        bucketDao.deleteItem(item, bucketId);
    }
}
