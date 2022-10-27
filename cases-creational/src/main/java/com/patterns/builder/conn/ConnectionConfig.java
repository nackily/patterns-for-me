package com.patterns.builder.conn;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 连接配置
 *
 * @author coder
 * @date 2022-09-09 16:35:59
 * @since 1.0.0
 */
public class ConnectionConfig {

    private final String url;                     // 连接地址
    private final int port;                       // 端口
    private final int protocolMagic;              // 协议魔数
    private final String protocolVersion;         // 协议版本
    private final String clientId;                // 客户端识别号
    private final String username;                // 连接账号
    private final byte[] password;                // 密码
    private final boolean keepAlive;              // 保持连接
    private final byte[] heartbeat;               // 固定心跳包
    private final int maxKeepAliveSecs;           // 连接保持最长空闲时间（秒），超过该时间没有心跳，断开连接
    private final int connectTimeoutSecs;         // 连接超时时间（秒）
    private final int readTimeoutSecs;            // 读取超时时间（秒）

    public ConnectionConfig(ConnectionBuilder builder) {
        // 默认url 127.0.0.1
        this.url = builder.url == null ? "127.0.0.1" : builder.url;
        // 默认端口 13920
        this.port = builder.port == null ? 13920 : builder.port;
        // 默认协议识别号 0xFF2345
        this.protocolMagic = builder.protocolMagic == null ? 0xff2345 : builder.protocolMagic;
        // 默认协议版本 1-0-0
        this.protocolVersion = builder.protocolVersion == null ? "1-0-0" : builder.protocolVersion;
        this.clientId = builder.clientId;
        this.username = builder.username;
        this.password = builder.password.getBytes(StandardCharsets.UTF_8);
        this.keepAlive = builder.keepAlive;
        // 默认心跳包
        this.heartbeat = builder.heartbeat == null ? new byte[]{0xf, 0x3} : builder.heartbeat;
        // 默认最大空闲 5s
        this.maxKeepAliveSecs = Math.max(builder.maxKeepAliveSecs, 5);
        // 默认连接超时 2s
        this.connectTimeoutSecs = Math.max(builder.connectTimeoutSecs, 2);
        // 默认读取超时 1s
        this.readTimeoutSecs = Math.max(builder.readTimeoutSecs, 1);
    }

    public void print() {
        System.out.println("ConnectionConfig {\n" +
                "   ip = " + url + "\n" +
                "   port = " + port + "\n" +
                "   protocolMagic = " + protocolMagic + "\n" +
                "   protocolVersion = " + protocolVersion + "\n" +
                "   clientId = " + clientId + "\n" +
                "   username = " + username + "\n" +
                "   password = " + Arrays.toString(password) + "\n" +
                "   keepAlive = " + keepAlive + "\n" +
                "   heartbeat = " + Arrays.toString(heartbeat) + "\n" +
                "   maxKeepAliveSecs = " + maxKeepAliveSecs + "s\n" +
                "   connectTimeoutSecs = " + connectTimeoutSecs + "s\n" +
                "   readTimeoutSecs = " + readTimeoutSecs + "s\n" +
                '}');
    }

    /**
     * 连接构建器
     */
    public static class ConnectionBuilder {
        private final String url;
        private final Integer port;
        private Integer protocolMagic;
        private String protocolVersion;
        private String clientId;
        private String username;
        private String password;
        private boolean keepAlive;
        private byte[] heartbeat;
        private int maxKeepAliveSecs;
        private int connectTimeoutSecs;
        private int readTimeoutSecs;

        public ConnectionBuilder(String url, int port) {
            this.url = url;
            this.port = port;
        }

        public ConnectionBuilder protocolMagic(int protocolMagic) {
            this.protocolMagic = protocolMagic;
            return this;
        }

        public ConnectionBuilder protocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public ConnectionBuilder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public ConnectionBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ConnectionBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ConnectionBuilder keepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public ConnectionBuilder heartbeat(byte[] heartbeat) {
            this.heartbeat = heartbeat;
            return this;
        }

        public ConnectionBuilder maxKeepAliveSecs(int maxKeepAliveSecs) {
            this.maxKeepAliveSecs = maxKeepAliveSecs;
            return this;
        }

        public ConnectionBuilder connectTimeoutSecs(int connectTimeoutSecs) {
            this.connectTimeoutSecs = connectTimeoutSecs;
            return this;
        }

        public ConnectionBuilder readTimeoutSecs(int readTimeoutSecs) {
            this.readTimeoutSecs = readTimeoutSecs;
            return this;
        }

        public ConnectionConfig build() {
            return new ConnectionConfig(this);
        }
    }
}
