package ru.mirea.khokhlov.task2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyApi {
    @GET("episode")
    Call<Result> getEpisodes();
}
