package br.ufscar.dc.pooa.parser;

import br.ufscar.dc.pooa.model.Headline;

import java.util.List;

public interface NewsParser {
    String getId();
    List<Headline> parse();
}
