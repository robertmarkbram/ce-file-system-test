package com.org.rmb.filesystemtest;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileRepositoryUnitTest {

   public static final String FILE_CONTENT = "Two wrongs don't make a left turn.";

   public static final String RESOURCE_FILE_NAME = "testFile.txt";

   private final FileSystemService fileSystemService = new FileSystemService();

   @Test
   @DisplayName("Should delete file")
   void givenCurrentSystem_whenDeletingFile_thenFileHasBeenDeleted() throws Exception {
      final FileSystem fileSystem = Jimfs.newFileSystem();
      final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
      Files.copy(getResourceFilePath(), resourceFilePath);

      fileSystemService.delete(resourceFilePath);

      assertFalse(Files.exists(resourceFilePath));
   }

   private Path getResourceFilePath() throws URISyntaxException {
      URI uri = ClassLoader.getSystemResource(RESOURCE_FILE_NAME).toURI();
      String mainPath = Paths.get(uri).toString();
      return Paths.get(uri);
   }

   @Test
   @DisplayName("Should read the content of the file")
   void givenOsxSystem_whenReadingFile_thenContentIsReturned() throws Exception {
      final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.osX());
      final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
      Files.copy(getResourceFilePath(), resourceFilePath);

      final String content = fileSystemService.read(resourceFilePath);

      assertEquals(FILE_CONTENT, content);
   }

   @Test
   @DisplayName("Should create a file on a file system")
   void givenUnixSystem_whenCreatingFile_thenCreatedInPath() {
      final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
      final String fileName = "newFile.txt";
      final Path pathToStore = fileSystem.getPath("");

      fileSystemService.create(pathToStore, fileName);

      assertTrue(Files.exists(pathToStore.resolve(fileName)));
   }

   @Test
   @DisplayName("Should update the content of the file")
   void givenWindowsSystem_whenUpdatingFile_thenContentHasChanged() throws Exception {
      final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
      final Path resourceFilePath = fileSystem.getPath(RESOURCE_FILE_NAME);
      Files.copy(getResourceFilePath(), resourceFilePath);
      final String newContent = "I'm updating you.";

      final String content = fileSystemService.update(resourceFilePath, newContent);

      assertEquals(newContent, content);
      assertEquals(newContent, fileSystemService.read(resourceFilePath));
   }

}
