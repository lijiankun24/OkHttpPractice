package com.lijiankun24.okhttppractice.okhttp.websocket;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * OkHttpWebSocket.java
 * <p>
 * Created by lijiankun on 17/9/1.
 */

public class OkHttpWebSocket {

    private static final MockWebServer sWebServer = new MockWebServer();

    private static WebSocket sWebClient = null;

    private static int sMsgCount = 0;

    private static Timer sTimer = null;

    public static void main(String[] args) {
        System.out.println("========== OkHttpWebSocket main");
        initMockServer();
        initWebSocketClient("ws://" + sWebServer.getHostName() + ":" + sWebServer.getPort() + "/");
    }

    private static void startTask() {
        sTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (sWebClient != null) {
                    sWebClient.send("========== 我是lijiankun24");
                    sMsgCount++;
                }
            }
        };
        sTimer.schedule(task, 0, 2000);
    }

    private static void initWebSocketClient(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                sWebClient = webSocket;
                System.out.println("========== client onOpen ");
                startTask();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("========== client onMessage ");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("========== client onClosing ");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("========== client onClosed ");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("========== client onFailure ");
            }
        });
    }

    private static void initMockServer() {
        sWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                System.out.println("========== withWebSocketUpgrade onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("========== withWebSocketUpgrade onMessage " + text);
                if (sMsgCount >= 5) {
                    sTimer.cancel();
                    sWebClient.close(1000, "closed by server");
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("========== withWebSocketUpgrade onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("========== withWebSocketUpgrade onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("========== withWebSocketUpgrade onFailure");
            }
        }));
    }
}
