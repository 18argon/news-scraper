package br.ufscar.dc.pooa;

import br.ufscar.dc.pooa.csv.HeadlineCsvWriter;
import br.ufscar.dc.pooa.model.Headline;
import br.ufscar.dc.pooa.parser.BBCPortugueseParser;
import br.ufscar.dc.pooa.parser.EstadaoParser;
import br.ufscar.dc.pooa.parser.NewsParser;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        HeadlineCsvWriter csvWriter = new HeadlineCsvWriter.Builder()
                .setOutDir("./out/")
                .build();

        List<NewsParser> parsers = new ArrayList<>();
//        parsers.add(new BBCPortugueseParser());
        parsers.add(new EstadaoParser());

        for (NewsParser parser : parsers) {
            List<Headline> headlines = parser.parse();

            // Print on the screen
            headlines.forEach(System.out::println);

            // Write csv file
            csvWriter.write("bcc-pt", headlines);
        }
    }
}
