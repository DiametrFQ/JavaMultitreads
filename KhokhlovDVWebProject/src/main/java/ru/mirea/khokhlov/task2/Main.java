package ru.mirea.khokhlov.task2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RickAndMortyApi api = retrofit.create(RickAndMortyApi.class);

        api.getEpisodes().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Episode> episodes = response.body().getEpisodes();

                    Episode maxCharacterEpisode = episodes.stream()
                            .max(Comparator.comparingInt(ep -> ep.getCharacters().size()))
                            .orElse(null);

                    if (maxCharacterEpisode != null) {
                        System.out.println("Эпизод с максимальным количеством персонажей:");
                        System.out.println("Название: " + maxCharacterEpisode.getName());
                        System.out.println("Код эпизода: " + maxCharacterEpisode.getEpisodeCode());
                        System.out.println("Количество персонажей: " + maxCharacterEpisode.getCharacters().size());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
