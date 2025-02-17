package br.usp.each.saeg.badua.report.html.page;

import br.usp.each.saeg.badua.cli.HTMLCoverageWriter;
import br.usp.each.saeg.badua.core.analysis.MethodCoverage;
import br.usp.each.saeg.badua.core.analysis.SourceLineDefUseChain;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.ILinkable;
import org.jacoco.report.internal.html.resources.Styles;

import java.io.IOException;
import java.util.Collection;

public class MethodPage extends TablePage {

    private Collection<SourceLineDefUseChain> defUses;
    private final ILinkable sourcePage;

    /**
     * Página com tabela que apresenta as DUAs de um método de uma classe
     * @param methodNode
     * @param parent
     * @param sourcePage - Página de source onde o código fonte da pagina foi renderizado
     * @param folder
     */
    public MethodPage(final MethodCoverage methodNode,
                      final ReportPage parent,
                      final ILinkable sourcePage,
                      final ReportOutputFolder folder) {
        super(methodNode, parent, folder, new HTMLCoverageWriter(), true);
        this.defUses = methodNode.getDefUses();
        this.sourcePage = sourcePage;
    }

    /**
     * Renderização das páginas dinamicas das DUAs existentes no metodo
     * Esse método alimenta a list - vinda do TablePage - que ira renderizar a tabela de DUAs nesta página
     * @throws IOException
     */
    public void render() throws IOException {
        for (SourceLineDefUseChain dua : defUses) {
            addItem(new DuaItem(dua,sourcePage));
        }
        super.render();
    }

    @Override
    protected String getFileName() {
        String shortname = node.getName();
        return shortname + "().html";

    }

    @Override
    public String getLinkLabel() {
        return this.getFileName();
    }

    @Override
    public String getLinkStyle() {
        return Styles.EL_METHOD;
    }
}
