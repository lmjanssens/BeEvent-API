package nl.hsleiden.service;

import java.util.*;

public class CollectionDataService<T> {
    public Collection<T> getToBeDeleted(Collection<T> oldCollection, Collection<T> newCollection) {
        Collection<T> tmpCollection = oldCollection;

        try {
            tmpCollection = new ArrayList<>(oldCollection);

            for (T newElement : newCollection) {
                for (T oldElement : oldCollection) {
                    if (newElement.equals(oldElement)) {
                        tmpCollection.remove(newElement);
                    }
                }
            }
        } catch (NullPointerException exception) {

        }

        return tmpCollection;
    }

    public Collection<T> getToBeSaved(Collection<T> oldCollection, Collection<T> newCollection) {
        return this.getToBeDeleted(newCollection, oldCollection);
    }

    private Collection<T> mergeCollection(Collection<T> collection1, Collection<T> collection2) {
        ArrayList<T> list = new ArrayList<>();

        try {
            for (T element : collection1) {
                list.add(element);
            }

            for (T element : collection2) {
                list.add(element);
            }
        } catch (NullPointerException | ConcurrentModificationException exception) {

        }



        return list;
    }

    private Collection<T> substractCollection(Collection<T> collection1, Collection<T> collection2) {
        Collection<T> tmpCollection = collection1;

        try {
            for (T element : collection2)
                tmpCollection.remove(element);
        } catch (ConcurrentModificationException exception) {

        }

        return tmpCollection;
    }

    /**
     * Create the correct Set to store in the entity model
     * @param collection
     * @param toBeSaved
     * @param toBeDeleted
     * @return
     */
    public Set<T> getDefinitiveCollection(Collection<T> collection, Collection<T> toBeSaved, Collection<T> toBeDeleted) {
        return new HashSet<T>(
                mergeCollection(
                        substractCollection(collection, toBeDeleted),
                        toBeSaved
                )
        );
    }
}
