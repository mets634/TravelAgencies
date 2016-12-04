package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 12/4/2016.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A class that converts an object
 * to a byte array and vice-versa.
 */
public class ClassSerializer {
    /**
     * Private constructor to disable the ability
     * to create an instance of the class.
     */
    private ClassSerializer() {}

    /**
     * Method to convert an object to a byte array.
     * @param obj The object to convert.
     * @return A byte array containing the object.
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        try{
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(obj);

            return b.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method to convert a byte array to an object.
     * @param bytes A yte array to convert.
     * @return An object taken from the byte array.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try{
            ByteArrayInputStream b = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(b);

            return o.readObject();
            }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
