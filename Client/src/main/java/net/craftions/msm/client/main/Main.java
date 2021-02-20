/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.main;

import net.craftions.msm.api.rsa.Keys;
import net.craftions.msm.client.gui.Home;
import net.craftions.msm.client.network.Client;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        KeyPair kp = Keys.gen(2048);
        Client.PRIVATE_KEY = kp.getPrivate();
        Client.PUBLIC_KEY = kp.getPublic();
        // Client.create("localhost", 40800, "mctzock", "81f175d0c002804ca5b8da150b79ab44");
        Home.show();
    }
}
