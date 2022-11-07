package read.write.lock.l24;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class InventoryRepository {

    TreeMap<Integer, Integer> price = new TreeMap<>();

    public int getItemsForPriceRange(Integer lowerPrice, Integer upperPrice){
        Integer lowerKey = price.ceilingKey(lowerPrice);
        Integer upperKey = price.floorKey(upperPrice);
        System.out.println(price);
        System.out.println("lowerKey" + lowerKey);
        System.out.println("upperKey" + upperKey);
        if(lowerKey == null || upperKey == null)
            return 0;

        NavigableMap<Integer, Integer> subMapForRangeKeys = price.subMap(lowerKey, true, upperKey, true);
        AtomicReference<Integer> totalItems = new AtomicReference<>(0);
        subMapForRangeKeys.values().forEach(i -> {
            totalItems.set(totalItems.get() + i);
        });

        return totalItems.get();
    }

    public void addItem(Integer priceKey){

        Integer items = price.get(priceKey);

        if(items == null){
            price.put(priceKey,1);
        }else {
            price.put(priceKey, items + 1);
        }
    }

    public void deleteItems(Integer priceKey){

        Integer items = price.get(priceKey);

        if(items == null || items == 1){
            price.remove(priceKey);
        }else {
            price.put(priceKey, items - 1);
        }

    }

}
