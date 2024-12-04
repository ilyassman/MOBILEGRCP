package ma.project.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.project.stubs.BankServiceGrpc;
import ma.project.stubs.ConvertCurrencyRequest;
import ma.project.stubs.ConvertCurrencyResponse;


public class GrpcClient {
    public static void main(String[] args) {
        // Create a channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5555)
                .usePlaintext()
                .build();

        // Create a blocking stub
        BankServiceGrpc.BankServiceBlockingStub blockingStub =
                BankServiceGrpc.newBlockingStub(channel);

        // Create request
        ConvertCurrencyRequest request = ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setCurrencyTo("USD")
                .setAmount(7500)
                .build();

        // Make the call
        ConvertCurrencyResponse response = blockingStub.convert(request);

        // Print the response
        System.out.println("Response received from server:");
        System.out.println("From: " + response.getCurrencyFrom());
        System.out.println("To: " + response.getCurrencyTo());
        System.out.println("Amount: " + response.getAmount());
        System.out.println("Converted: " + response.getResult());
    }
}