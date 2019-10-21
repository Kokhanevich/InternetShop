package kokhanevych.internetshop.dao;

import java.util.List;
import kokhanevych.internetshop.model.Item;

public interface ItemDao {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    void delete(Long id);

    List<Item> getAll();
}
