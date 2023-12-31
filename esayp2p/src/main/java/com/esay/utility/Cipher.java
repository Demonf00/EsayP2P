package com.esay.utility;

public class Cipher {
    private static final String SECRET_KEY = "fedcbaabcdeffedcbaabcdeffedcba";
    private String plainText;
    private String cipheredText;

    //dumb cipher
    public Cipher (String plainText) {
        this.plainText = plainText;
        this.cipheredText = dumbCrypto();
    }

    public String dumbCrypto() {
        char[] plainChar = plainText.toCharArray();
        char[] keyChar = SECRET_KEY.toCharArray();
        for (int i = 0; i < plainChar.length; ++i) {
            plainChar[i] = (char) (plainChar[i] ^ keyChar[i]);
        }
        String text = new String(plainChar);
        return text;
    }

    // public String dumbDeCrypto() {
    //     String text;
    //     return text;
    // }

    public String getPlainText() {
        return this.plainText;
    }

    public String getCipheredText() {
        return this.cipheredText;
    }

    public void setPlainText(String text) {
        this.plainText = text;
        this.cipheredText = dumbCrypto();
    }

}
