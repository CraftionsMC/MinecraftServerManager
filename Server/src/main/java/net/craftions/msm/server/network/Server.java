/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.msm.server.network;

import net.craftions.msm.api.rsa.Keys;
import net.craftions.msm.server.user.UserDB;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Server {

    public static PrivateKey PRIVATE_KEY = null;
    public static PublicKey PUBLIC_KEY = null;
    public static Boolean needNewThread = true;

    public static void create() throws IOException {
        ServerSocket server = new ServerSocket(40800);
        while(true) {
            if(needNewThread){
                // threaded client listener
                Thread clientThread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            // disable creation of empty threads
                            needNewThread = false;
                            Socket client = server.accept();
                            System.out.println("Client connected from " + client.getRemoteSocketAddress());
                            // enable thread creation
                            needNewThread = true;
                            // initialize i/o streams
                            Scanner s = new Scanner(client.getInputStream());
                            PrintWriter w = new PrintWriter(client.getOutputStream(), true);
                            // send current public key
                            w.println(Base64.getEncoder().encodeToString(PUBLIC_KEY.getEncoded()));
                            // read client public key
                            String clientPubKeyRaw = s.nextLine();
                            PublicKey clientPubKey = Keys.fromString(clientPubKeyRaw);
                            // check user credentials
                            String userName = Keys.decrypt(Base64.getDecoder().decode(s.nextLine().getBytes()), PRIVATE_KEY);
                            String pwd = Keys.decrypt(Base64.getDecoder().decode(s.nextLine().getBytes()), PRIVATE_KEY);
                            System.out.println(userName);
                            System.out.println(pwd);
                            Boolean enable = false;
                            if(userName.equals("mctzock") && pwd.equals("81f175d0c002804ca5b8da150b79ab44")){
                                enable = true;
                                w.println("welcome!");
                            }else {
                                w.println("failed");
                            }
                            while(s.hasNextLine() && enable){
                                System.out.println("Awaiting command...");
                                String rawCommand = Keys.decrypt(Base64.getDecoder().decode(s.nextLine().getBytes()), PRIVATE_KEY);
                                System.out.println("Received command: " + rawCommand);
                                String[] args = rawCommand.split(" ");
                                String command = args[0];
                                if(command.equals("get-msm-info")){
                                    if(args[1].equals("version")){
                                        w.println(Base64.getEncoder().encodeToString(Keys.encrypt("1.0", clientPubKey)));
                                    }else if(args[1].equals("name")){
                                        w.println(Keys.encrypt(Base64.getEncoder().encodeToString("Standard-Server".getBytes()), clientPubKey));
                                    }
                                }else if(command.equals("close")){
                                    break;
                                }
                            }
                            System.out.println("Closing connection");
                            // close streams and free memory
                            w.close();
                            s.close();
                            client.close();
                            // !! deprecated !!
                            currentThread().stop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        }
                        super.run();
                    }
                };
                // start a new client listener
                clientThread.start();
            }
        }
    }
}
