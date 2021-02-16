/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.server.main;

import net.craftions.msm.api.rsa.Keys;
import net.craftions.msm.server.network.Server;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPair kp = Keys.gen(2048);
        Server.PRIVATE_KEY = kp.getPrivate();
        Server.PUBLIC_KEY = kp.getPublic();
        Server.create();
    }
}
