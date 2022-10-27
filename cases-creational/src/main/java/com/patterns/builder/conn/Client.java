package com.patterns.builder.conn;

import java.nio.charset.StandardCharsets;

/**
 * Client
 *
 * @author coder
 * @date 2022-09-09 17:15:44
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        ConnectionConfig conn = new ConnectionConfig.ConnectionBuilder("localhost", 13920)
                .clientId("048a8a0c41644b53b57e4b612ddabd92")
                .username("root")
                .password("root@!Az")
                .keepAlive(true)
                .heartbeat("hello".getBytes(StandardCharsets.UTF_8))
                .maxKeepAliveSecs(12)
                .connectTimeoutSecs(3)
                .readTimeoutSecs(1)
                .build();
        conn.print();
    }
}
