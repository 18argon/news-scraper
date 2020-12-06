package br.ufscar.dc.pooa.csv;

import br.ufscar.dc.pooa.util.DirectoryCreator;
import br.ufscar.dc.pooa.model.Headline;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HeadlineCsvWriter {
    private final char delimiter;
    private final String outDir;
    private final boolean useQuotes;

    private HeadlineCsvWriter(char delimiter, String outDir, boolean useQuotes) {
        this.delimiter = delimiter;
        this.outDir = outDir;
        this.useQuotes = useQuotes;

        DirectoryCreator.create(outDir);
    }

    public void write(String sourceId, List<Headline> headlines) {
        String strNow = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
        String filename = String.format("%s/%s-%s.csv", outDir, strNow, sourceId);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("Tipo;Notícia;Link");
            for (Headline h : headlines) {
                if (useQuotes) {
                    pw.println(String.format("%s%c%s%c%s",
                            addQuotes(h.getType()), delimiter,
                            addQuotes(h.getTitle()), delimiter,
                            addQuotes(h.getLink())
                    ));
                } else {
                    pw.println(String.format("%s%c%s%c%s",
                            h.getType(), delimiter,
                            h.getTitle(), delimiter,
                            h.getLink()
                    ));
                }
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
        private Boolean useQuotes;

        public Builder setDelimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder setOutDir(String outDir) {
            this.outDir = outDir;
            return this;
        }

        public Builder setUseQuotes(boolean useQuotes) {
            this.useQuotes = useQuotes;
            return this;
        }

        public HeadlineCsvWriter build() {
            if (delimiter == null) {
                delimiter = ';';
            }

            if (outDir == null) {
                outDir = "";
            }

            if (useQuotes == null) {
                useQuotes = true;
            }

            return new HeadlineCsvWriter(delimiter, outDir, useQuotes);
        }
    }
}
