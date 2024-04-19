package com.programming.techie.javasftpserver.schedu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class MySftpServer {

    private Log log = LogFactory.getLog(MySftpServer.class);

    @Value("${sftp.username}")
    private String userName;
    @Value("${sftp.port}")
    private Integer port;
    @Value("${sftp.password}")
    private String passWord;
    @Value("${sftp.rootDir}")
    private String rootDir;
    @Value("${sftp.ip}")
    private String ip;
    @Value("${sftp.portList}")
    private String portList;

    @PostConstruct
    public void startServer() throws IOException {
        start();
    }

    private void start() throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost(ip);
        sshd.setPort(port);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("ftpserver.jks")));
        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
        sshd.setPasswordAuthenticator((username, password, session) -> username.equals(username) && password.equals(password));
        //sshd.setPublickeyAuthenticator(new AuthorizedKeysAuthenticator(new File("src/main/resources/ftpserver.jks")));

        // 设置用户根目录
        VirtualFileSystemFactory fileSystemFactory = new VirtualFileSystemFactory();
        fileSystemFactory.setUserHomeDir(userName, Paths.get(rootDir));
        sshd.setFileSystemFactory(fileSystemFactory);

        sshd.start();
        log.info("SFTP server started");
    }

}
