package ma.project.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import ma.project.service.BankGrpcService;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder
                .forPort(5555)
                .addService((BindableService) new BankGrpcService())
                .build();

        System.out.println("Starting gRPC server on port 5555...");
        server.start();

        // Keep the server running
        server.awaitTermination();
    }
}
