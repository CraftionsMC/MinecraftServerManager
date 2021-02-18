/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.client.network;

import net.craftions.msm.api.rsa.Keys;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Client {

    public static PrivateKey PRIVATE_KEY = null;
    public static PublicKey PUBLIC_KEY = null;

    public static void create() throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Socket socket = new Socket("localhost",40800);
        Scanner s = new Scanner(socket.getInputStream());
        PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
        String serverPubKeyRaw = s.nextLine();
        PublicKey serverPubKey = Keys.fromString(serverPubKeyRaw);
        w.println(Base64.getEncoder().encodeToString(PUBLIC_KEY.getEncoded()));
        w.println(Base64.getEncoder().encodeToString(Keys.encrypt("mctzock", serverPubKey)));
        w.println(Base64.getEncoder().encodeToString(Keys.encrypt("81f175d0c002804ca5b8da150b79ab44", serverPubKey)));
        Boolean en = false;
        String r = s.nextLine();
        if(r.equals("welcome!")){
            en = true;
        }
        if(en){
            System.out.println("Accepted");
            w.println(Base64.getEncoder().encodeToString(Keys.encrypt("get-msm-info version", serverPubKey)));
            r = s.nextLine();
            System.out.println("Server-Version: " + Keys.decrypt(Base64.getDecoder().decode(r.getBytes()), PRIVATE_KEY));
        }else {
            System.out.println("Denied: Wrong credentials");
            w.close();
            s.close();
            socket.close();
        }
        System.out.println("Closing...");
        w.close();
        s.close();
        socket.close();
    }
}
