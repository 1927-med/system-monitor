package main.java.com.systemmonitor;

public interface InMemoryDataStorage {

    /**
     * Saves the given data to the in-memory storage.
     *
     * @param data The data to be saved.
     */
    void saveData(String data);

    /**
     * Retrieves the data from the in-memory storage.
     *
     * @return The stored data.
     */
    String retrieveData();

    /**
     * Clears all data from the in-memory storage.
     */
    void clearData();
} InMemoryDataStorage {
    
}
