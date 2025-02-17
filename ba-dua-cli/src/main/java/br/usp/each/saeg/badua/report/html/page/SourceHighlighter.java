package br.usp.each.saeg.badua.report.html.page;

import org.jacoco.report.internal.html.HTMLElement;
import org.jacoco.report.internal.html.resources.Styles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;

public class SourceHighlighter{

    private final Locale locale;
    private String lang;

    /**
     * Essa classe auxilia na renderização do código fonte a partir do reader, modelando-o para uma página HTML
     * @param locale
     */
    public SourceHighlighter (final Locale locale) {
        this.locale = locale;
        lang = "java";
    }

    public void setLanguage(final String lang) {
        this.lang = lang;
    }

    public void render(final HTMLElement parent,
                       final Reader contents)
        throws IOException {
        final HTMLElement pre = parent.pre(Styles.SOURCE + " lang-" + lang + " linenums");
        final BufferedReader lineBuffer = new BufferedReader(contents);
        String line;
        int nr = 0;
        while ((line = lineBuffer.readLine()) != null) {
            nr++;
            renderCodeLine(pre, line, nr);
        }
    }

    /**
     * Renderização das linhas do código fonte pra página html
     *
     * @param pre       -> tag HTML
     * @param linesrc   -> linha obtida no buffer
     * @param lineNr    -> número da linha
     * @throws IOException
     */
    private void renderCodeLine(final HTMLElement pre,
                                final String linesrc,
                                final int lineNr)
        throws IOException {
        final String lineId = "L" + Integer.toString(lineNr);
        pre.span(lineId,lineId).text(linesrc);
        pre.text("\n");
    }

    private HTMLElement span(HTMLElement parent, String idattr) throws IOException {
        HTMLElement span = parent.span();
        span.attr("id", idattr);
        return span;
    }
}
