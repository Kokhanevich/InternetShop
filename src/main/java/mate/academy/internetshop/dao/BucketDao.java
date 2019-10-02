package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    void delete(Long bucketId);

    Bucket addItem(Long bucketId, Long itemId);

    List<Item> getAllItems(Long bucketId);

    void deleteItem(Item item, Long bucketId);

    void clear(Long bucketId);
}
