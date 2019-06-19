package eu.hopu.dto;

public class SecurityMode {
    private String mode;
    private String pskIdentity;
    private String pskKey;
    private String xPkClient;
    private String yPkClient;
    private String params;
    private String prvNumber;
    private String yPkServer;
    private String xPkServer;
    private String serverKeystorePath;
    private String clientKeystorePath;

    public SecurityMode() {
    }

    public SecurityMode(String mode, String pskIdentity, String pskKey, String xPkClient, String yPkClient, String params, String prvNumber, String yPkServer, String xPkServer, String serverKeystorePath, String clientKeystorePath) {
        this.mode = mode;
        this.pskIdentity = pskIdentity;
        this.pskKey = pskKey;
        this.xPkClient = xPkClient;
        this.yPkClient = yPkClient;
        this.params = params;
        this.prvNumber = prvNumber;
        this.yPkServer = yPkServer;
        this.xPkServer = xPkServer;
        this.serverKeystorePath = serverKeystorePath;
        this.clientKeystorePath = clientKeystorePath;
    }

    public void setClientKeystorePath(String clientKeystorePath) {
        this.clientKeystorePath = clientKeystorePath;
    }

    public String getxPkClient() {
        return xPkClient;
    }

    public void setxPkClient(String xPkClient) {
        this.xPkClient = xPkClient;
    }

    public String getyPkClient() {
        return yPkClient;
    }

    public void setyPkClient(String yPkClient) {
        this.yPkClient = yPkClient;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPskIdentity() {
        return pskIdentity;
    }

    public void setPskIdentity(String pskIdentity) {
        this.pskIdentity = pskIdentity;
    }

    public String getPskKey() {
        return pskKey;
    }

    public void setPskKey(String pskKey) {
        this.pskKey = pskKey;
    }

    public String getXPkClient() {
        return xPkClient;
    }

    public void setXPkClient(String xPkClient) {
        this.xPkClient = xPkClient;
    }

    public String getYPkClient() {
        return yPkClient;
    }

    public void setYPkClient(String yPkClient) {
        this.yPkClient = yPkClient;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPrvNumber() {
        return prvNumber;
    }

    public void setPrvNumber(String prvNumber) {
        this.prvNumber = prvNumber;
    }

    public String getYPkServer() {
        return yPkServer;
    }

    public String getyPkServer() {
        return yPkServer;
    }

    public void setyPkServer(String yPkServer) {
        this.yPkServer = yPkServer;
    }

    public String getXPkServer() {
        return xPkServer;
    }

    public String getxPkServer() {
        return xPkServer;
    }

    public void setxPkServer(String xPkServer) {
        this.xPkServer = xPkServer;
    }

    public String getServerKeystorePath() {
        return serverKeystorePath;
    }

    public void setServerKeystorePath(String serverKeystorePath) {
        this.serverKeystorePath = serverKeystorePath;
    }

    public String getClientKeystorePath() {
        return clientKeystorePath;
    }
}
