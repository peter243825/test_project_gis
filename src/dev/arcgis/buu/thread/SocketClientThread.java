/**
 * dev.arcgis.buu.thread / SocketClientThread.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-9
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.thread;

import android.text.TextUtils;

import dev.arcgis.buu.utils.Constants;
import dev.arcgis.buu.utils.FileUtils;
import dev.arcgis.buu.utils.L;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * socket通讯线程类
 */
public class SocketClientThread extends Thread {
    private final String fileName;

    public SocketClientThread(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(Constants.kServerAddress, Constants.kServerPort);
            final DataInputStream din = new DataInputStream(socket.getInputStream());
            final DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            String str = din.readUTF();
            if (!TextUtils.isEmpty(str)) {
                L.d(SocketClientThread.class, "received: " + str);
            }
            dout.writeUTF("AgentInfo");
            str = din.readUTF();
            if (!TextUtils.isEmpty(str)) {
                L.d(SocketClientThread.class, "received: " + str);
            }
            dout.writeUTF(fileName);
            dout.writeLong(FileUtils.getFileLength(fileName));
            final String fileContent = FileUtils.loadFromFile(fileName);
            dout.write(fileContent.getBytes());
            str = din.readUTF();
            if (!TextUtils.isEmpty(str)) {
                L.d(SocketClientThread.class, "received: " + str);
            }
            //关闭输出流
            dout.close();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (final Exception e) {}
        }
    }
}
