package io.repsy.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSystemStorageService implements StorageService {
  private final Path root;

  public FileSystemStorageService(String baseDir) {
    this.root = Paths.get(baseDir);
  }

  @Override
  public void store(String pkg, String version, String filename, InputStream data) {
    try {
      Path dir = root.resolve(pkg).resolve(version);
      Files.createDirectories(dir);
      try (OutputStream os = Files.newOutputStream(
               dir.resolve(filename),
               StandardOpenOption.CREATE,
               StandardOpenOption.TRUNCATE_EXISTING)) {
        data.transferTo(os);
      }
    } catch (Exception e) {
      throw new StorageException("Failed to store file", e);
    }
  }

  @Override
  public InputStream retrieve(String pkg, String version, String filename) {
    try {
      return Files.newInputStream(root.resolve(pkg).resolve(version).resolve(filename));
    } catch (Exception e) {
      throw new StorageException("Failed to retrieve file", e);
    }
  }
}
