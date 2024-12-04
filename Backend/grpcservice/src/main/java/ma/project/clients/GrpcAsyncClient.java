package ma.project.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.project.stubs.BankServiceGrpc;
import ma.project.stubs.ConvertCurrencyRequest;
import ma.project.stubs.ConvertCurrencyResponse;


public class GrpcAsyncClient {
    public static void main(String[] args) throws InterruptedException {
        // Create a channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5555)
                .usePlaintext()
                .build();

        // Create an async stub
        BankServiceGrpc.BankServiceStub asyncStub = BankServiceGrpc.newStub(channel);

        // Create request
        ConvertCurrencyRequest request = ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(7500)
                .build();

        // Make async call
        asyncStub.getCurrencyStream(request, new StreamObserver<ConvertCurrencyResponse>() {
            @Override
            public void onNext(ConvertCurrencyResponse response) {
                System.out.println("--------------------");
                System.out.println("Received response:");
                System.out.println("From: " + response.getCurrencyFrom());
                System.out.println("To: " + response.getCurrencyTo());
                System.out.println("Amount: " + response.getAmount());
                System.out.println("Converted: " + response.getResult());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
            }
        });

        // Keep the application alive to receive async responses
        Thread.sleep(10000);

        // Shutdown the channel
        channel.shutdown();
    }
}