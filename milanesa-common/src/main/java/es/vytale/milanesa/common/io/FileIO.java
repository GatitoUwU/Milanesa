package es.vytale.milanesa.common.io;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class FileIO {

    @SneakyThrows
    public static void writeFile(File file, String data) {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
        fileWriter.close();
    }

    @SneakyThrows
    public static String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        return stringBuilder.toString();
    }

}