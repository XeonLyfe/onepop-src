// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.file;

import java.util.stream.Stream;
import java.nio.file.FileVisitOption;
import java.util.zip.ZipOutputStream;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class FileUtil
{
    public static final int BUFFER = 4096;
    
    public static void createFileIfNeeded(final Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
    }
    
    public static void createFolderIfNeeded(final Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectory(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
    }
    
    public static void deleteFile(final String path) {
        final File file = new File(path);
        file.delete();
    }
    
    public static void extractZipFolder(final String zipPath, final String extractedPath) {
        try {
            final File file = new File(zipPath);
            final ZipFile zip = new ZipFile(file);
            new File(extractedPath).mkdir();
            final Enumeration zipFilesEntries = zip.entries();
            while (zipFilesEntries.hasMoreElements()) {
                final ZipEntry entry = zipFilesEntries.nextElement();
                final File destFile = new File(extractedPath, entry.getName());
                final File destinationFileParent = destFile.getParentFile();
                destinationFileParent.mkdir();
                if (!entry.isDirectory()) {
                    final BufferedInputStream bufferedInputStream = new BufferedInputStream(zip.getInputStream(entry));
                    final byte[] data = new byte[4096];
                    final FileOutputStream fileOutputStream = new FileOutputStream(destFile);
                    final BufferedOutputStream dest = new BufferedOutputStream(fileOutputStream, 4096);
                    int currentByte;
                    while ((currentByte = bufferedInputStream.read(data, 0, 4096)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    bufferedInputStream.close();
                }
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    public static void compactZipFolder(final String sourceDirPath, final String zipFilePath) throws IOException {
        final Path path1 = Files.createFile(Paths.get(zipFilePath, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
        final Path path2 = Paths.get(sourceDirPath, new String[0]);
        try (final ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(path1, new OpenOption[0]))) {
            final Stream<Path> paths = Files.walk(path2, new FileVisitOption[0]);
            paths.filter(path -> !Files.isDirectory(path, new LinkOption[0])).forEach(path -> {
                final ZipEntry zipEntry = new ZipEntry(path2.relativize(path).toString());
                try {
                    zip.putNextEntry(zipEntry);
                    Files.copy(path, zip);
                    zip.close();
                }
                catch (IOException exc) {
                    exc.printStackTrace();
                }
                return;
            });
        }
    }
}
