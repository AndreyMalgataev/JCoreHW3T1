import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder("Процесс установки:\n");
        String installPath = "C://Games/JCoreHW3/Games";

        //install block
        install(installPath, log);

        //uninstall block
//        uninstall(installPath, log);
//        System.out.println(log.toString());
    }

    private static void makeFileOrDirectoryAndLog(Boolean isFolder, String absolutePath, StringBuilder log) {
        File object = new File(absolutePath);
        if (isFolder) {
            if (object.mkdir()) {
                log.append("Папка " + object.getAbsolutePath() + " создана\n");
            } else {
                log.append("Папка " + object.getAbsolutePath() + " не была создана\n");
            }
        } else {
            try {
                if (object.createNewFile()) {
                    log.append("Файл " + object.getAbsolutePath() + " создан\n");
                } else {
                    log.append("Файл " + object.getAbsolutePath() + " не был создан\n");
                }
            } catch (IOException e) {
                log.append("Фйал " + object.getAbsolutePath() + " не был создан. Ошибка:" + e.getMessage());
            }
        }
    }

    public static void install(String installPath, StringBuilder log) {
        makeFileOrDirectoryAndLog(true, installPath + "/src", log);
        makeFileOrDirectoryAndLog(true, installPath + "/res", log);
        makeFileOrDirectoryAndLog(true, installPath + "/savegames", log);
        makeFileOrDirectoryAndLog(true, installPath + "/temp", log);

        makeFileOrDirectoryAndLog(true, installPath + "/src/main", log);
        makeFileOrDirectoryAndLog(true, installPath + "/src/test", log);

        makeFileOrDirectoryAndLog(false, installPath + "/src/main/Main.java", log);
        makeFileOrDirectoryAndLog(false, installPath + "/src/main/Utils.java", log);

        makeFileOrDirectoryAndLog(true, installPath + "/res/drawables", log);
        makeFileOrDirectoryAndLog(true, installPath + "/res/vectors", log);
        makeFileOrDirectoryAndLog(true, installPath + "/res/icons", log);

        makeFileOrDirectoryAndLog(false, installPath + "/temp/temp.txt", log);

        saveLogs(installPath, log);
    }

    public static void uninstall(String installPath, StringBuilder log) {
        File dir = new File(installPath);

        try {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                Files.walk(Paths.get(installPath))
                        .map(Path::toFile)
                        .filter(folder -> !Path.of(installPath).toFile().getAbsolutePath().toString()
                                .equals(folder.getAbsolutePath().toString()))
                        .sorted(Comparator.reverseOrder())
                        .forEach(file -> {
                            if (file.delete()) {
                                log.append("Удаление " + file.getAbsolutePath() + "\n");
                            }
                        });
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveLogs(String installPath, StringBuilder log) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(installPath + "/temp/temp.txt"))) {
            bw.write(log.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
