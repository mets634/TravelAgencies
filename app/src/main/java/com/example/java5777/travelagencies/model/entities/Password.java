package com.example.java5777.travelagencies.model.entities;

import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by yonah on 12/1/2016.
 */

/**
 * A class to contain a password in a secure way.
 * The class uses a hash function for creating a
 * password and validating a password.
 */
public class Password {
    /**
     * Password class constructor. Generates hash using
     * the argument as input.
     * @param originalPassword
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public Password(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.salt = generateSalt();
        this.hash = generateHash(originalPassword, salt);
    }

    /**
     * Class password constructor without
     * generating new data.
     * @param hash A given hash result.
     * @param salt The salt that was used to compute the hash.
     */
    public Password(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Password{" +
                "hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    /**
     * A method to check if given password
     * matches the hash stored.
     * @param password Password to try.
     * @return Whether hashes match.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean validatePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hash.equals( generateHash(password, salt) );
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
// class fields

    /**
     * Factor against dictionary attacks. The
     * higher the number, the longer it will
     * take to complete the hash calculation.
     */
    private final static int ITERATIONS = 1000;

    /**
     * Result of hash function.
     */
    private String hash;

    /**
     * The salt used during hash
     * calculation. Used to validate
     * a password given.
     */
    private String salt;

    private final static int SALT_LENGTH = 16;


    // helper static methods

    /**
     * Method to generate a cryptographically
     * random 'salt'
     * @return The random salt.
     * @throws NoSuchAlgorithmException
     */
    @NonNull
    private static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); // get a cryptographic random number generator

        // build salt
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);

        return bytesToHex(salt);
    }

    @NonNull
    private static String generateHash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // calculate hash
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded(); // get result
        return bytesToHex(hash); // return hash as hexadecimal String
    }

    /**
     * A static method to turn a byte[] to String.
     * @param in byte[] to convert.
     * @return Resulting String.
     */
    @NonNull
    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b)); // turn each byte into hex
        }
        return builder.toString();
    }
}
