package com.wbw.note.encrypt;

public class RC4Encryption {

    private byte[] key;
    private int[] sbox;
    private static final int SBOX_LENGTH = 256;

    public RC4Encryption(byte[] key) {
        this.key = key;
        initializeSBox();
    }

    private void initializeSBox() {
        sbox = new int[SBOX_LENGTH];
        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }

        int j = 0;
        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
            swap(i, j);
        }
    }

    private void swap(int i, int j) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public byte[] encrypt(byte[] data) {
        byte[] encrypted = new byte[data.length];
        int i = 0;
        int j = 0;

        for (int k = 0; k < data.length; k++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j);
            int temp = (sbox[i] + sbox[j]) % SBOX_LENGTH;
            encrypted[k] = (byte) (data[k] ^ sbox[temp]);
        }

        return encrypted;
    }

    public byte[] decrypt(byte[] encryptedData) {
        RC4Encryption rc4 = new RC4Encryption(key);
        return rc4.encrypt(encryptedData);
    }


    public static byte[] encryptData(byte[] key, byte[] data) {
        RC4Encryption rc4 = new RC4Encryption(key);
        return rc4.encrypt(data);
    }

    public static byte[] decryptData(byte[] key, byte[] encryptedData) {
        RC4Encryption rc4 = new RC4Encryption(key);
        return rc4.decrypt(encryptedData);
    }
}
