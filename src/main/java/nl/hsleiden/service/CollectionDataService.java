package nl.hsleiden.service;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionService<T> {
    public Collection<T> getMissingElements(Collection<T> collection, Collection<T> found) {
        for (T foundElement : found) {
            for (T collectionElement : collection) {
                if (foundElement.equals(collectionElement)) {
                    collection.remove(collectionElement);
                    continue;
                }
            }
        }

        return collection;
    }
}
