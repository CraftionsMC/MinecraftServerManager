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

    public static Boolean accepted = false;

    public static PrintWriter w = null;
    public static Scanner s = null;
    public static PublicKey serverPubKey = null;

    public static void create(String host, int port, String username, String password) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Socket socket = new Socket(host, port);
        s = new Scanner(socket.getInputStream());
        w = new PrintWriter(socket.getOutputStream(), true);
        String serverPubKeyRaw = s.nextLine();
        serverPubKey = Keys.fromString(serverPubKeyRaw);
        w.println(Base64.getEncoder().encodeToString(PUBLIC_KEY.getEncoded()));
        w.println(Base64.getEncoder().encodeToString(Keys.encrypt(username, serverPubKey)));
        w.println(Base64.getEncoder().encodeToString(Keys.encrypt(password, serverPubKey)));
        Boolean en = false;
        String r = s.nextLine();
        if(r.equals("welcome!")){
            en = true;
        }
        if(en){
            accepted = true;
            System.out.println("Accepted");
            w.println(Base64.getEncoder().encodeToString(Keys.encrypt("get-msm-info version", serverPubKey)));
            r = Keys.decrypt(Base64.getDecoder().decode(s.nextLine().getBytes()), PRIVATE_KEY);
            System.out.println("Server-Version: " + r);
        }else {
            System.out.println("Denied: Wrong credentials");
            w.close();
            s.close();
            socket.close();
        }
    }

    public static String executeCommand(String msg) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(accepted){
            w.println(Base64.getEncoder().encodeToString(Keys.encrypt(msg, serverPubKey)));
            return Keys.decrypt(Base64.getDecoder().decode(s.nextLine().getBytes()), PRIVATE_KEY);
        }
        return "Connection closed.";
    }
}
