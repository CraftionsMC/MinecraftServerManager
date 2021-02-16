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
                            PrintWriter w = new PrintWriter(client.getOutputStream());
                            // send current public key
                            w.println(new String(PUBLIC_KEY.getEncoded()));
                            // read client public key
                            String clientPubKeyRaw = s.nextLine();
                            PublicKey clientPubKey = Keys.fromString(clientPubKeyRaw);
                            // check user credentials
                            String userName = Keys.decrypt(s.nextLine().getBytes(), PRIVATE_KEY);
                            String pwd = Keys.decrypt(s.nextLine().getBytes(), PRIVATE_KEY);
                            Boolean enable = false;
                            if(UserDB.users.containsKey(userName) && UserDB.users.get(userName).equals(pwd)){
                                enable = true;
                            }
                            while(s.hasNextLine() && enable){
                                String rawCommand = Keys.decrypt(s.nextLine().getBytes(StandardCharsets.UTF_8), PRIVATE_KEY);
                                String[] args = rawCommand.split(" ");
                                String command = args[0];
                                if(command.equals("get-msm-info")){
                                    if(args[1].equals("version")){
                                        w.write(new String(Keys.encrypt("1.0", clientPubKey), StandardCharsets.UTF_8));
                                    }else if(args[1].equals("name")){
                                        w.write(new String(Keys.encrypt("Standard-Server", clientPubKey), StandardCharsets.UTF_8));
                                    }
                                }else if(command.equals("close")){
                                    break;
                                }
                            }
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
