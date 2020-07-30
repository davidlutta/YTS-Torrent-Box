package com.davidlutta.ytsapp.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.davidlutta.ytsapp.api.ApiResponse;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NetworkBoundResource";

    private MediatorLiveData<Resource<ResultType>> results = new MediatorLiveData<Resource<ResultType>>();
    private AppExecutors appExecutors;

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    private void init() {
        results.setValue((Resource<ResultType>) Resource.loading(null));

        // livedata from db
        final LiveData<ResultType> dbSource = loadFromDb();

        results.addSource(dbSource, resultType -> {
            results.removeSource(dbSource);

            // get data from network if shouldfetch is true
            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource);
            } else {
                results.addSource(dbSource, resultType1 -> { // if false fetch data from db
                    setValue(Resource.success(resultType1));
                });
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponseLiveData = createCall();
        results.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        results.addSource(apiResponseLiveData, response -> {
            results.removeSource(apiResponseLiveData);
            results.removeSource(dbSource);
            Log.d(TAG, "fetchFromNetwork: run: Attempting to fetch data from network");
            if (response instanceof ApiResponse.ApiSuccessResponse) {
                Log.d(TAG, "fetchFromNetwork: ApiSuccessResponse");
                appExecutors.diskIO().execute(() -> {
                    // Saving response to db
                    saveCallResult((RequestType) processResponse((ApiResponse.ApiSuccessResponse) response));
                    appExecutors.mainThread().execute(() -> {
                        results.addSource(loadFromDb(), newData -> {
                            setValue(Resource.success(newData));
                        });
                    });
                });
            } else if (response instanceof ApiResponse.ApiEmptyResponse) {
                Log.d(TAG, "fetchFromNetwork: ApiEmptyResponse");
                appExecutors.mainThread().execute(() -> {
                    results.addSource(loadFromDb(), newData -> setValue(Resource.success(newData)));
                });
            } else if (response instanceof ApiResponse.ApiErrorResponse) {
                Log.d(TAG, "fetchFromNetwork: ApiErrorResponse");
                onFetchFailed();
                results.addSource(dbSource, resultType -> setValue(Resource.error(((ApiResponse.ApiErrorResponse) response).getErrorMessage(), resultType)));
            }
        });
    }

    /**
     * Setting new value to LiveData
     * Must be done on MainThread
     *
     * @param newValue
     */
    private void setValue(Resource<ResultType> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    private ResultType processResponse(ApiResponse.ApiSuccessResponse response) {
        return (ResultType) response.getBody();
    }

    // Called to save the result of the API response into the database.
    public abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    public abstract boolean shouldFetch(@Nullable ResultType data);


    // Called to get the cached data from the database.
    @NonNull
    public abstract LiveData<ResultType> loadFromDb();


    // Called to create the API call.
    @NonNull
    public abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    public abstract void onFetchFailed();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return results;
    }
}
