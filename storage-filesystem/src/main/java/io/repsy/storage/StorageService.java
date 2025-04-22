package ip.repsy.storage;

import java.io.InputStream;

public interface StorageService[
    void store(String pkg, String version, String fileName, InputStream data);
    InputStream retrieve (String pkg, String version, String fileName);
]