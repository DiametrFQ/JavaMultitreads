package ru.mirea.khokhlov.stonks;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class StonksClient {
    public static void main(String[] args) throws IOException, SQLException {
        Retrofit client = new Retrofit
                .Builder()
                .baseUrl("https://www.cbr.ru")
                .addConverterFactory(JacksonConverterFactory.create(new XmlMapper()))
                .build();

        StonksService stonksService = client.create(StonksService.class);

        LocalDate localDate = LocalDate.of(2000, 8, 5);

        Response<DailyCurs> response = stonksService
                .getDailyCurs(localDate
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).execute();

        DailyCurs dailyCurs = response.body();

        assert dailyCurs != null;
        Optional<Valute> maxValute = dailyCurs.getValutes().stream()
                .filter(valute -> !valute.getName().equals("СДР (специальные права заимствования)"))
                .max(Comparator.comparingDouble(Valute::getValue));

        DatabaseService databaseService = new DatabaseServiceImpl();

        if (maxValute.isPresent()) {
            System.out.println(maxValute.get());

            Valute mv = maxValute.get();

            // databaseService.saveMaxValuteOfDate("ХохловДВ", mv, localDate);
        }

        Valute valute = databaseService.getValuteOfDate(localDate);

        if (valute != null) {
            System.out.println("Валюта из БД: " + valute.getName() + ", " + valute.getValue() + " руб.");
        }

        System.exit(0);
    }
}
