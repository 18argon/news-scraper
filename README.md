# News Scraper
A aplicação realiza a extração de manchetes de sites de notícia, e realiza o processamento destas.

- Foi utilizado algum design pattern?

Foi utilizado o design pattern Builder para criar o objeto HeadlineCsvWriter, com o objetivo de criar um objeto que
tenha uma configuração padrão, mas possa ser personalizado durante a criação sem precisar especificar toda a configuração
Para 

- Como eu faço para incluir um novo site de notícias? Onde eu tenho que mexer?

Uma nova fonte de notícias pode ser adicionada ao criar uma classe que implementa a ‘interface’ NewsParser. Implementando
assim o método getId que deve fornecer um id para identificar a fonte, e o método parse que deve realizar
o parseamento da notícia e retornar uma lista de Headline.

A nova classe pode utilizar-se do JSoup, que foi utilizado neste projeto ou outra ferramenta para realizar o parseamento
de HTML. Para utilizar uma nova ferramenta será necessário adicioná-la ao projeto.

```java
// NewSourceParser.java
import br.ufscar.dc.pooa.model.Headline;
import br.ufscar.dc.pooa.parser.NewsParser;

import java.util.List;

public class NewSourceParser implements NewsParser {
    @Override
    public String getId() {
        // id usado para identificar a fonte.
        return "new-source-id";
    }

    @Override
    public List<Headline> parse() {
        // Aqui pode ser adicionado o parseamento da página da notícia
        // com a sua ferramenta favorita.
        return null;
    }
}
```

Após criar a classe, uma instância dela deve ser adicionada à lista de parsers que serão utilizados na função main em App.java.


``` java
// App.java
// ....
List<NewsParser> parsers = new ArrayList<>();
parsers.add(new BBCPortugueseParser());
parsers.add(new EstadaoParser());
parsers.add(new NewSourceParser());
// ....
```

Uma fonte pode ser removida da execução ao ser retirada da lista de parsers.

A nova classe pode utilizar-se do JSoup, que foi utilizado neste projeto ou outra ferramenta para realizar o parseamento
de HTML. Para utilizar uma nova ferramenta será necessário adicioná-la ao projeto.

- Como eu faço para incluir um algoritmo para processar as notícias extraídas? Onde eu tenho que mexer?

Um novo algoritmo pode ser adicionado na classe App, após o parseamento ter sido
realizado. O parseamento fornece uma lista de objetos Headline, com o tipo, título e link da notícia.

Por exemplo, uma ferramenta que escreve os dados da Headline em um arquivo JSON pode ser adicionada assim.

```java
// App.java
// ...
for (NewsParser parser : parsers) {
    List<Headline> headlines = parser.parse();

    // Uma nova ferramenta de processamento pode ser adionada aqui
    HeadlineJSONWriter.write(parser.getId(), headlines);
    
    // ...
}
// ....
```

