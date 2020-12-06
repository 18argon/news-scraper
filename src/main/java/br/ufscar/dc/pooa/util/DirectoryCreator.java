package br.ufscar.dc.pooa.util;

import java.io.File;

public class DirectoryCreator {
    public static boolean create(String path) {
        File dir = new File(path);
        if (dir.exists()) return true;
        return dir.mkdir();
    }
}
