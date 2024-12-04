package ma.project.service;

import io.grpc.stub.StreamObserver;
import ma.project.stubs.BankServiceGrpc;
import ma.project.stubs.ConvertCurrencyRequest;
import ma.project.stubs.ConvertCurrencyResponse;


public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void convert(ConvertCurrencyRequest request, StreamObserver<ConvertCurrencyResponse> responseObserver) {
        // Extract request parameters
        String currencyFrom = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();
        double amount = request.getAmount();

        // Simple conversion logic (using a fixed rate for demo)
        double convertedAmount = amount * 11.4; // Example fixed rate

        // Build the response
        ConvertCurrencyResponse response = ConvertCurrencyResponse.newBuilder()
                .setCurrencyFrom(currencyFrom)
                .setCurrencyTo(currencyTo)
                .setAmount(amount)
                .setResult(convertedAmount)
                .build();

        // Send the response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCurrencyStream(ConvertCurrencyRequest request,
                                  StreamObserver<ConvertCurrencyResponse> responseObserver) {
        // Simulate streaming response with multiple rates
        double[] rates = {11.3, 11.4, 11.5, 11.6, 11.7};

        for (double rate : rates) {
            ConvertCurrencyResponse response = ConvertCurrencyResponse.newBuilder()
                    .setCurrencyFrom(request.getCurrencyFrom())
                    .setCurrencyTo(request.getCurrencyTo())
                    .setAmount(request.getAmount())
                    .setResult(request.getAmount() * rate)
                    .build();

            responseObserver.onNext(response);

            // Add small delay between responses
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }
}