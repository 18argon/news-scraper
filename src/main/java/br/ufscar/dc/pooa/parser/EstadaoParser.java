package br.ufscar.dc.pooa.parser;

import br.ufscar.dc.pooa.model.Headline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EstadaoParser implements NewsParser {
    @Override
    public List<Headline> parse() {
        ArrayList<Headline> headlines = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.estadao.com.br/").get();

            Elements titles = doc.select(".title");
            for (Element title : titles) {
                Headline h = parseHeadline("Destaque", title);
                if (h == null) continue;
                headlines.add(h);
            }

            Elements modules = doc.select(".modulos-ajax-home");
            for (Element module : modules) {
                String id = module.id();
                doc = Jsoup.connect(String.format("https://www.estadao.com.br/modulo/%s", id)).get();
                titles = doc.select(".title");
                for (Element title : titles) {
                    Headline h = parseHeadline("Outros", title);
                    if (h == null) continue;
                    headlines.add(h);
                }
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return headlines;
    }

    private Headline parseHeadline(String type, Element headline) {
        Element anchor = headline.selectFirst("a");
        if (anchor == null) {
            Element parent = headline.parent();
            if (parent.tag().getName().equals("a")) {
                anchor = parent;
            } else {
                return null;
            }
        }
        return new Headline(type, headline.text(), anchor.attr("href"));
    }
}
