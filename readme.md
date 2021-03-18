# File System Mocking with Jimfs

1. Create a file system service as an abstraction or facade over the file system.
2. In tests, you can mock the file system service directly so callers never touch any file system directly.
3. Or use `Jimfs` as demonstrated here, whereby the file system service acts directly upon the file system objects you send it, but it using the Java In-Memory File System.

An advantage of `Jimfs` is that since it is all in-memory, actual files will never be created, and you don't have to worry about the JVM crashing before clean-up routines can be performed: if you lose the JVM, the files disappear too!


Resources.

1. [Jimfs](https://github.com/google/jimfs)

    > Jimfs is an in-memory file system for Java 7 and above, implementing the [java.nio.file](http://docs.oracle.com/javase/7/docs/api/java/nio/file/package-summary.html) abstract file system APIs.

2. [File System Mocking with Jimfs](https://www.baeldung.com/jimfs-file-system-mocking) from Baeldung.
    1. [Source code for that article is on github](https://github.com/eugenp/tutorials/tree/master/testing-modules/mocks).
