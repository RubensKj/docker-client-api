package com.rubenskj.resource;

import com.github.dockerjava.api.model.Container;
import com.rubenskj.service.DockerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/docker/containers")
public class DockerResource {

    @Inject
    DockerService dockerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Container> checkContainers() {
        return dockerService.checkContainers();
    }

    @GET
    @Path("/{containerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Container checkContainerStatus(@PathParam("containerId") String containerId) throws IOException {
        return dockerService.checkContainerById(containerId);
    }
}
