package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;

public interface BucketService {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    void deleteById(Long bucketId);

    void deleteByBucket(Bucket bucket);

    Bucket addItem(Long bucketId, Long itemId);

    Bucket clear(Long bucketId);

    List getAllItems(Long bucketId);
}
