package com.electronic_ecommerce.application.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class CompletableFutureUtils {

    private CompletableFutureUtils() {}

    public static <T> List<T> collectResultsFromFutures(List<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList())
                .join();
    }
}
