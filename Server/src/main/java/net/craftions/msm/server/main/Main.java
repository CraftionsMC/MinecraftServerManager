/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.server.main;

import net.craftions.msm.api.rsa.Keys;
import net.craftions.msm.server.network.Server;
import net.craftions.msm.server.user.UserDB;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        // Sample user ** will be removed in the future **
        UserDB.users.put("mctzock", "81f175d0c002804ca5b8da150b79ab44");
        // generate RSA keys
        KeyPair kp = Keys.gen(2048);
        Server.PRIVATE_KEY = kp.getPrivate();
        Server.PUBLIC_KEY = kp.getPublic();
        // start server
        Server.create(41530);
    }
}
