package com.reactiverestserver.service;

import com.reactiverestserver.domain.Item;
import com.reactiverestserver.repository.ItemReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemReactiveRepository itemReactiveRepository;

    public Flux<Item> findAllItems() {
        return itemReactiveRepository.findAll();
    }

    public Mono<Item> findItemById(String id) {
        return itemReactiveRepository.findById(id);
    }

    public Mono<Item> createItem(Item item) {
        return itemReactiveRepository.save(item);
    }

    public Mono<Void> removeItem(String id) {
        return itemReactiveRepository.deleteById(id);
    }

    public Mono<Item> updateItem(String id, Item item){
        return itemReactiveRepository.findById(id)
                .flatMap(currentItem -> {
                    currentItem.setPrice(item.getPrice());
                    currentItem.setDescription(item.getDescription());
                    return itemReactiveRepository.save(currentItem);
                });
    }
}
