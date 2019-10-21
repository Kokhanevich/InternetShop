package kokhanevych.internetshop.service;

import java.util.List;

import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.Item;

public interface BucketService {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    void delete(Long bucketId);

    Bucket addItem(Long bucketId, Long itemId);

    Bucket clear(Long bucketId);

    List<Item> getAllItems(Long bucketId);

    void deleteItem(Item item, Long bucketId);
}
