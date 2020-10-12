package com.momotenko.task4.model;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
    @Override
    public Class findClass(String fileName) {
        byte[] b = loadClassFromFile(fileName);

        String fullName = this.getClass().getCanonicalName();
        fullName = fullName.substring(0, fullName.lastIndexOf('.'));
        fullName += ('.' +  fileName);

        return defineClass(fullName, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName){
        String editedFileName = fileName.replace('.', File.separatorChar) + ".class";
        InputStream inputStream = this.getClass().getResourceAsStream(editedFileName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i = 0;

        try {
            while ((i = inputStream.read()) != -1) {
                byteArrayOutputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
