package kokhanevych.internetshop.service.impl;

import java.util.List;

import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.lib.Service;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id);
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public void delete(Long id) {
        itemDao.delete(id);
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }
}
