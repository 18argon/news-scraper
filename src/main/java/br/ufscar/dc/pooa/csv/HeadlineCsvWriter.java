package br.ufscar.dc.pooa.csv;

import br.ufscar.dc.pooa.util.DirectoryCreator;
import br.ufscar.dc.pooa.model.Headline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HeadlineCsvWriter {
    private final char delimiter;
    private final String outDir;

    private HeadlineCsvWriter(char delimiter, String outDir) {
        this.delimiter = delimiter;
        this.outDir = outDir;

        DirectoryCreator.create(outDir);
    }

    public void write(String sourceId, List<Headline> headlines) {
        String strNow = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
        String filename = String.format("%s/%s-%s.csv", outDir, strNow, sourceId);
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(filename)))) {
            pw.println("Tipo;Notícia;Link");
            for (Headline h : headlines) {
                pw.println(String.format("%s%c%s%c%s",
                        addQuotes(h.getType()), delimiter,
                        addQuotes(h.getTitle()), delimiter,
                        addQuotes(h.getLink())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addQuotes(String field) {
        field = field.replace("\"", "\"\"");
        return String.format("\"%s\"", field);
    }

    public static class Builder {
        private Character delimiter;
        private String outDir;

        public Builder setDelimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder setOutDir(String outDir) {
            this.outDir = outDir;
            return this;
        }

        public HeadlineCsvWriter build() {
            if (delimiter == null) {
                delimiter = ';';
            }

            if (outDir == null) {
                outDir = "./out/";
            }

            return new HeadlineCsvWriter(delimiter, outDir);
        }
    }
}
