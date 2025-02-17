package br.usp.each.saeg.badua.report.html.page;

import br.usp.each.saeg.badua.cli.HTMLCoverageWriter;
import br.usp.each.saeg.badua.core.analysis.ClassCoverage;
import br.usp.each.saeg.badua.core.analysis.MethodCoverage;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.ILinkable;
import org.jacoco.report.internal.html.resources.Styles;

import java.io.IOException;
import java.util.Collection;

public class ClassPage extends TablePage{

	private Collection<MethodCoverage> methods;
	private final ILinkable sourcePage;

	/**
	 * Página com tabela que apresenta os métodos de uma classe
	 * @param classNode
	 * @param parent
	 * @param sourcePage - Página de source onde o código fonte da pagina foi renderizado
	 * @param folder
	 */
	public ClassPage(final ClassCoverage classNode,
					 final ReportPage parent,
					 final ILinkable sourcePage,
					 final ReportOutputFolder folder) {
		super(classNode, parent, folder, new HTMLCoverageWriter());
		this.methods = classNode.getMethods();
		this.sourcePage = sourcePage;
	}

	public void render() throws IOException{
		renderMethods();
		super.render();
	}

	/**
	 * Renderização das páginas de método com suas tabela de DUAs
	 * Esse método alimenta a list - vinda do TablePage - que ira renderizar a tabela nesta página
	 * @throws IOException
	 */
	private void renderMethods() throws IOException {
		for(MethodCoverage mc : methods)
		{
			// Getting method info
			final String methodName = mc.getName();
			final String folderName = methodName.length() == 0 ? "default"
					: methodName.replace('/', '.');

			//Rendering the method page
			final MethodPage page = new MethodPage(mc, this, sourcePage, folder.subFolder(folderName));
			page.render();

			//Adding method page to classpage
			addItem(page);
		}
	}

	protected String getOnload() {
		return "initialSort(['breadcrumb', 'coveragetable'])";
	}

	protected String getFileName() {
		return "index.html";
	}

	public String getLinkStyle() {
		return Styles.EL_CLASS;
	}

}
