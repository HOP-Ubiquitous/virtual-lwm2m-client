package eu.hopu.devices;


import com.google.gson.JsonParseException;
import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.LocationDto;
import eu.hopu.dto.SecurityMode;
import eu.hopu.objects.DeviceObject;
import eu.hopu.objects.LocationObject;
import eu.hopu.objects.RouteLocationObject;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.util.Hex;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.*;
import java.util.ArrayList;
import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.client.object.Security.*;

//import eu.javaspecialists.tjsn.concurrency.stripedexecutor.StripedExecutorService;
//import org.eclipse.californium.core.network.CoapEndpoint;
//import org.eclipse.californium.core.network.config.NetworkConfig;
//import org.eclipse.californium.core.observe.ObservationStore;
//import org.eclipse.californium.scandium.DTLSConnector;
//import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
//import org.eclipse.leshan.core.californium.EndpointFactory;
//import org.eclipse.leshan.util.NamedThreadFactory;
//import java.net.InetSocketAddress;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;

public abstract class DeviceBase {

//    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("CoapServer#%d"));
//
//    private static ScheduledExecutorService getExecutor() {
//        return executor;
//    }

    public static final DeviceBase NULL = new DeviceBase() {
        @Override
        List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
            return new ArrayList<>();
        }
    };

    private static int DEVICE_PORT = 40000;
    private SecurityMode security;

    private String name;
    private String serverUrl;
    private String serverPort;
    private int lifetime;
    private DeviceDto deviceDto;
    private static final String LOCAL_ADDRESS = "0.0.0.0";
    private int localPort;
    private LocationDto location;

    private boolean isBootstrap;

    private static List<ObjectModel> models;

    static {
        try (InputStream stream = LeshanClient.class.getClassLoader().getResourceAsStream("objectspec/objectspec.json")) {
            models = ObjectLoader.loadJsonStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DeviceBase() {
    }

    public DeviceBase(String name, String serverUrl, String serverPort, int lifetime, DeviceDto device, LocationDto location, Boolean isBootstrap,
                      SecurityMode security) {
        this.name = name;
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;
        this.lifetime = lifetime;
        this.deviceDto = device;
        this.localPort = getFreePort();
        this.location = location;
        this.isBootstrap = isBootstrap;
        this.security = security;
    }


    private synchronized int getFreePort() {
        return DEVICE_PORT++;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public DeviceDto getDevice() {
        return deviceDto;
    }

    public void setDevice(DeviceDto deviceDto) {
        this.deviceDto = deviceDto;
    }

    public String getLocalAddress() {
        return LOCAL_ADDRESS;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public boolean isBootstrap() {
        return isBootstrap;
    }

    public void setBootstrap(boolean bootstrap) {
        isBootstrap = bootstrap;
    }

    public LeshanClient getLeshanClient() {


        ObjectsInitializer objectsInitializer = getObjectInitializer(models);
        List<LwM2mObjectEnabler> enablers = getDeviceEnabledObjects(objectsInitializer);

        LeshanClientBuilder builder = new LeshanClientBuilder(getName());
        builder.setLocalAddress(getLocalAddress(), getLocalPort());
        builder.setObjects(enablers);


//        NetworkConfig coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
//        coapConfig.set(NetworkConfig.Keys.DEDUPLICATOR, NetworkConfig.Keys.NO_DEDUPLICATOR);
//
//        builder.setCoapConfig(coapConfig);
//
//        builder.setEndpointFactory(new EndpointFactory() {
//            @Override
//            public CoapEndpoint createUnsecuredEndpoint(InetSocketAddress address, NetworkConfig coapConfig, ObservationStore store) {
//                return new CoapEndpoint(address, coapConfig);
//            }
//
//            @Override
//            public CoapEndpoint createSecuredEndpoint(DtlsConnectorConfig dtlsConfig, NetworkConfig coapConfig, ObservationStore store) {
//                DTLSConnector dtlsConnector = new DTLSConnector(dtlsConfig);
//                dtlsConnector.setExecutor(new StripedExecutorService(getExecutor()));
//                return new CoapEndpoint(dtlsConnector, coapConfig, null, null);
//            }
//        });

        LeshanClient client = builder.build();
//        client.getCoapServer().setExecutor(getExecutor());
        return client;
    }

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {

        ObjectsInitializer initializer = new ObjectsInitializer(new LwM2mModel(DeviceBase.models));

        X509Certificate serverX509Cert;

        if (isBootstrap) {
            switch (getSecurity().getMode()) {
                case "noSec":
                    initializer.setInstancesForObject(SECURITY, noSecBootstap(getServerUrl() + ":" + getServerPort()));
                    initializer.setClassForObject(SERVER, Server.class);
                    break;
                case "psk":
                    initializer.setInstancesForObject(SECURITY, pskBootstrap(getServerUrl() + ":" + getServerPort(),
                            getSecurity().getPskIdentity().getBytes(), Hex.decodeHex(getSecurity().getPskKey().toCharArray())));
                    initializer.setClassForObject(SERVER, Server.class);
                    break;
                case "rpk":
                    PublicKey publicKey;
                    PublicKey publicServerKey;
                    PrivateKey privateKey;
                    try {
                        byte[] x = Hex.decodeHex(getSecurity().getXPkClient().toCharArray());
                        byte[] y = Hex.decodeHex(getSecurity().getYPkClient().toCharArray());
                        byte[] privateS = Hex.decodeHex(getSecurity().getPrvNumber().toCharArray());
                        byte[] xServer = Hex.decodeHex(getSecurity().getXPkServer().toCharArray());
                        byte[] yServer = Hex.decodeHex(getSecurity().getYPkServer().toCharArray());
                        String params = getSecurity().getParams();

                        AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
                        algoParameters.init(new ECGenParameterSpec(params));
                        ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);

                        ECPoint ecServerPoint = new ECPoint(new BigInteger(xServer), new BigInteger(yServer));
                        KeySpec publicKeyServerStore = new ECPublicKeySpec(ecServerPoint, parameterSpec);

                        ECPoint ecPoint = new ECPoint(new BigInteger(x), new BigInteger(y));
                        KeySpec publicKeyStore = new ECPublicKeySpec(ecPoint, parameterSpec);
                        KeySpec privateKeyStore = new ECPrivateKeySpec(new BigInteger(privateS), parameterSpec);

                        publicServerKey = KeyFactory.getInstance("EC").generatePublic(publicKeyServerStore);

                        publicKey = KeyFactory.getInstance("EC").generatePublic(publicKeyStore);
                        privateKey = KeyFactory.getInstance("EC").generatePrivate(privateKeyStore);
                    } catch (IllegalArgumentException | InvalidKeySpecException | NoSuchAlgorithmException
                            | InvalidParameterSpecException e) {
                        throw new JsonParseException("Invalid security info content", e);
                    }

                    initializer.setInstancesForObject(SECURITY, rpkBootstrap(getServerUrl() + ":" + getServerPort(),
                            publicKey.getEncoded(), privateKey.getEncoded(), publicServerKey.getEncoded()));
                    initializer.setClassForObject(SERVER, Server.class);
                    break;
            }

        } else {
            switch (getSecurity().getMode()) {
                case "noSec":
                    initializer.setInstancesForObject(SECURITY, noSec(getServerUrl() + ":" + getServerPort(), 123));
                    initializer.setInstancesForObject(SERVER, new Server(123, lifetime, BindingMode.U, false));
                    break;
                case "psk":
                    initializer.setInstancesForObject(SECURITY, psk(getServerUrl() + ":" + getServerPort(), 123,
                            getSecurity().getPskIdentity().getBytes(), getSecurity().getPskKey().getBytes(StandardCharsets.UTF_8)));
                    initializer.setInstancesForObject(SERVER, new Server(123, lifetime, BindingMode.U, false));
                    break;
                case "rpk":
                    PublicKey publicKey;
                    PublicKey publicServerKey;
                    PrivateKey privateKey;

                    try {
                        char[] serverKeyStorePwd = "server".toCharArray();
                        KeyStore serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        try (FileInputStream serverKeyStoreFile = new FileInputStream(getSecurity().getServerKeystorePath())) {
                            serverKeyStore.load(serverKeyStoreFile, serverKeyStorePwd);
                        }

                        serverX509Cert = (X509Certificate) serverKeyStore.getCertificate("server");
                    } catch (GeneralSecurityException | IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        byte[] x = Hex.decodeHex(getSecurity().getXPkClient().toCharArray());
                        byte[] y = Hex.decodeHex(getSecurity().getYPkClient().toCharArray());
                        byte[] privateS = Hex.decodeHex(getSecurity().getPrvNumber().toCharArray());
                        byte[] xServer = Hex.decodeHex(getSecurity().getXPkServer().toCharArray());
                        byte[] yServer = Hex.decodeHex(getSecurity().getYPkServer().toCharArray());
                        String params = getSecurity().getParams();

                        AlgorithmParameters algoParameters = AlgorithmParameters.getInstance("EC");
                        algoParameters.init(new ECGenParameterSpec(params));
                        ECParameterSpec parameterSpec = algoParameters.getParameterSpec(ECParameterSpec.class);

                        ECPoint ecServerPoint = new ECPoint(new BigInteger(xServer), new BigInteger(yServer));
                        KeySpec publicKeyServerStore = new ECPublicKeySpec(ecServerPoint, parameterSpec);

                        ECPoint ecPoint = new ECPoint(new BigInteger(x), new BigInteger(y));
                        KeySpec publicKeyStore = new ECPublicKeySpec(ecPoint, parameterSpec);
                        KeySpec privateKeyStore = new ECPrivateKeySpec(new BigInteger(privateS), parameterSpec);

                        publicServerKey = KeyFactory.getInstance("EC").generatePublic(publicKeyServerStore);

                        publicKey = KeyFactory.getInstance("EC").generatePublic(publicKeyStore);
                        privateKey = KeyFactory.getInstance("EC").generatePrivate(privateKeyStore);

                        initializer.setInstancesForObject(SECURITY, rpk(getServerUrl() + ":" + getServerPort(), 123,
                                publicKey.getEncoded(), privateKey.getEncoded(),
                                serverX509Cert != null ? serverX509Cert.getPublicKey().getEncoded() : publicServerKey.getEncoded()));
                        initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));

                    } catch (IllegalArgumentException | InvalidKeySpecException | NoSuchAlgorithmException
                            | InvalidParameterSpecException e) {
                        throw new JsonParseException("Invalid security info content", e);
                    }

                    break;
                case "x509":
                    try {
                        char[] clientKeyStorePwd = "client".toCharArray();
                        KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        try (FileInputStream clientKeyStoreFile = new FileInputStream(getSecurity().getClientKeystorePath())) {
                            clientKeyStore.load(clientKeyStoreFile, clientKeyStorePwd);
                        }

                        PrivateKey clientPrivateKeyFromCert = (PrivateKey) clientKeyStore.getKey("client", clientKeyStorePwd);
                        X509Certificate clientX509Cert = (X509Certificate) clientKeyStore.getCertificate("client");

                        char[] serverKeyStorePwd = "server".toCharArray();
                        KeyStore serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        try (FileInputStream serverKeyStoreFile = new FileInputStream(getSecurity().getServerKeystorePath())) {
                            serverKeyStore.load(serverKeyStoreFile, serverKeyStorePwd);
                        }

                        serverX509Cert = (X509Certificate) serverKeyStore.getCertificate("server");

                        initializer.setInstancesForObject(SECURITY, x509(getServerUrl() + ":" + getServerPort(), 123,
                                clientX509Cert.getEncoded(), clientPrivateKeyFromCert.getEncoded(), serverX509Cert.getEncoded()));
                        initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));

                    } catch (GeneralSecurityException | IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;

            }

        }

        initializer.setInstancesForObject(DEVICE, new DeviceObject(name,
                deviceDto.getType(), deviceDto.getBatteryStatus(), deviceDto.getBatteryLevel()));

        LocationDto location = getLocation();
        if (location != null)
            initLocationObject(initializer, location);

        return initializer;
    }

    private void initLocationObject(ObjectsInitializer initializer, LocationDto location) {
        if (location.hasRoute() && location.getRoute().size() > 0)
            initializer.setInstancesForObject(LOCATION,
                    new RouteLocationObject(
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getAltitude(),
                            location.getRoute().get(0).getLatitude(),
                            location.getRoute().get(0).getLongitude()
                    )
            );
        else
            initializer.setInstancesForObject(LOCATION, new LocationObject(location.getLatitude(), location.getLongitude(), location.getAltitude()));
    }

    abstract List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer);

    public String getModel() {
        return this.getClass().getSimpleName();
    }

    public SecurityMode getSecurity() {
        return security;
    }

    public void setSecurity(SecurityMode security) {
        this.security = security;
    }
}
