package kokhanevych.internetshop.service;

import java.util.List;

import kokhanevych.internetshop.model.Item;

public interface ItemService {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    void delete(Long id);

    List<Item> getAll();
}
