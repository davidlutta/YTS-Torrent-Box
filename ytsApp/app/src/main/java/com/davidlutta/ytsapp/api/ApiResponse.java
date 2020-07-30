package com.davidlutta.ytsapp.api;

import retrofit2.Response;

public class ApiResponse<T> {
    public ApiResponse<T> create(Throwable error) {
        return new ApiErrorResponse<>(!error.getMessage().equals("") ? error.getMessage() : "Unknown Error\n Check Connection");
    }

    public ApiResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            // if body is empty or response code is 204 (Empty)
            if (body == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        } else {
            String errorMessage = "";
            try {
                errorMessage = response.errorBody().string();
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = response.message();
            }
            return new ApiErrorResponse<>(errorMessage);
        }
    }

    public class ApiSuccessResponse<T> extends ApiResponse<T> {
        private T body;

        public ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public class ApiErrorResponse<T> extends ApiResponse<T> {
        private String errorMessage;

        public ApiErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public class ApiEmptyResponse<T> extends ApiResponse<T> {
    }
}
