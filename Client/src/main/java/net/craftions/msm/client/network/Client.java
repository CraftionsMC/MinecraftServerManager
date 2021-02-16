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
import java.security.*;
import java.util.Scanner;

public class Client {

    public static PrivateKey PRIVATE_KEY = null;
    public static PublicKey PUBLIC_KEY = null;

    public static void create() throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Socket socket = new Socket("localhost",40800);
        Scanner s = new Scanner(socket.getInputStream());
        PrintWriter w = new PrintWriter(socket.getOutputStream());
        PublicKey serverPubKey = Keys.fromString(s.nextLine());
        w.println(Keys.encrypt(new String(PUBLIC_KEY.getEncoded()), serverPubKey));
        w.println(Keys.encrypt("mctzock", serverPubKey));
        w.println(Keys.encrypt("81f175d0c002804ca5b8da150b79ab44", serverPubKey));
        Boolean en = false;
        if(s.nextLine().equals(Keys.decrypt("welcome!".getBytes(), PRIVATE_KEY))){
            en = true;
        }
        if(en){
            w.println(Keys.encrypt("get-msm-info version", serverPubKey));
            System.out.println("Server-Version: " + Keys.decrypt(s.nextLine().getBytes(), PRIVATE_KEY));
        }else {
            w.close();
            s.close();
            socket.close();
        }
    }
}
