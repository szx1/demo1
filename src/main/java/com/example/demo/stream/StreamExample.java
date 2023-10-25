package com.example.demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zengxi.song
 * @date 2023/9/27
 */

public class StreamExample {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setProperty("Item 1 Property");
        Item item2 = new Item();
        item2.setProperty("Item 2 Property");
        items.add(item1);
        items.add(item2);
        System.out.println("Original items: " + items);
        List<Item> modifiedItems = items.stream()
                .peek(item -> item.setProperty("Modified Property"))
                .collect(Collectors.toList());
        System.out.println("Original items: " + items);
        // Modify an item in the modifiedItems list
        modifiedItems.get(0).setProperty("New Modified Property");

        System.out.println("Original items: " + items);
        System.out.println("Modified items: " + modifiedItems);
    }
}

class Item {
    private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Item{" +
                "property='" + property + '\'' +
                '}';
    }
}

