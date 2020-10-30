

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    static Logger logger = Logger.getLogger(ClassInfo.class);

    @Override
    public Class findClass(String name) {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName)  {
      //  PropertyConfigurator.configure("/Users/mykolamedynsky/Desktop/5semester/tasks/task4/src/main/resources/log4j.properties");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
