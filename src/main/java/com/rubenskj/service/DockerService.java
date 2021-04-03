package com.rubenskj.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class DockerService {

    private static DockerClient dockerClient;

    private DockerClient getInstance() {
        if (dockerClient != null) {
            return dockerClient;
        }

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);

        return dockerClient;
    }

    public List<Container> checkContainers() {
        DockerClient dockerClient = getInstance();

        return dockerClient.listContainersCmd().exec();
    }

    public Container checkContainerById(String containerId) {
        DockerClient dockerClient = getInstance();

        List<Container> containers = dockerClient.listContainersCmd().withIdFilter(Collections.singletonList(containerId)).exec();

        return containers.stream().findFirst().orElseThrow(() -> new RuntimeException("Container not found with this id. Id: " + containerId));
    }
}
