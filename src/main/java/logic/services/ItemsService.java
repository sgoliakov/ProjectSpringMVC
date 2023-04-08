package logic.services;

import logic.models.Item;
import logic.models.Person;
import logic.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findItemsByName(String itemName) {
        return itemsRepository.findAllByName(itemName);
    }

    public List<Item> findItemsByOwner(Person owner) {
        return itemsRepository.findAllByOwner(owner);
    }
}
