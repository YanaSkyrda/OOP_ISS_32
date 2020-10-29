package components;

import utils.Utils;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    //permanent generation(method area)
    @Override
    public Class<?> findClass(String path) throws ClassNotFoundException {

        //checking whether the file exists
        File file = new File(path);
        if (!file.isFile())
            throw new ClassNotFoundException("Error! No such a class: " + path);

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] b = new byte[(int) file.length()];
            int size = inputStream.read(b);
            return defineClass(Utils.getClassNameFromPath(path), b, 0, size);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException("Wrong byte code!");
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
