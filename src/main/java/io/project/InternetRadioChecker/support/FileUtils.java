package io.project.InternetRadioChecker.support;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static void saveToFile(byte[] data, Path path) {
        if (data == null) throw new IllegalArgumentException();
        try (OutputStream outputStream = Files.newOutputStream(path,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            outputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(byte[] data, Path path) {
        File cacheFile = new File(path.toAbsolutePath().toString());
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(cacheFile)){
            fileOutputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readBytesFromFile(Path path) {
        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            inputStream.transferTo(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
