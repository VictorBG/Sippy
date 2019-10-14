package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class FileIO
{
    /**
     * Reads a file from the specified path and returns
     * it as a vector of bytes
     * @param file      The file to read
     * @return          The file as a stream of bytes
     */
    public static byte[] readFile(File file) throws IOException
    {
        return Files.readAllBytes(file.toPath());
    }
}
