package br.ufscar.dc.pooa.parser;

import br.ufscar.dc.pooa.model.Headline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BBCPortugueseParser implements NewsParser {

    @Override
    public String getId() {
        return "bcc-portuguese";
    }

    @Override
    public List<Headline> parse() {
        ArrayList<Headline> headlines = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.bbc.com/portuguese").get();

            Elements sections = doc.select("section");
            for (Element section : sections) {
                Element type = section.selectFirst("span[class*=\"Title\"]");
                if (type == null) continue;

                Elements titles = section.select("h3[class*=\"Headline\"]");

                for (Element title : titles) {
                    String link = title.selectFirst("a").attr("href");
                    link = link.startsWith("/") ? String.format("https://www.bbc.com%s", link) : link;

                    headlines.add(new Headline(type.text(), title.text(), link));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headlines;
    }
}
