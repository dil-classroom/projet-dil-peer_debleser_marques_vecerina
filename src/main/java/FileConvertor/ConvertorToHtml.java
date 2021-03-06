package java.FileConvertor;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import java.io.*;
import java.nio.file.Path;

/**
 * Class to convert a markdown file to a html one
 * @author Dimitri De Bleser
 * @author André Marques Nora
 * @author Vincent Peer
 * @author Ivan Vecerina
 * @version 1.0
 */
public class ConvertorToHtml implements Helper<String> {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    /**
     * Method to create a template for the layout of markdown files
     * @param source path of file
     * @return template of markdown file to html
     * @throws IOException
     */
    public static Template getMdTemplate(Path source) throws IOException {
        TemplateLoader loader = new FileTemplateLoader(source.resolve("template").toFile());
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("md", new ConvertorToHtml());
        return handlebars.compile("layout");
    }

    /**
     * Method to change a markdown file to html file
     * @param markdown text
     * @return the conversion of a md to html
     */
    private Object mdToHtml(String markdown){
        Node document = parser.parse(markdown);
        return htmlRenderer.render(document);
    }

    /**
     * Method to apply the change of md to html
     * @param s text of the markdown
     * @param options not used
     * @return html file
     */
    @Override
    public Object apply(String s, Options options){
        return mdToHtml(s);
    }
}
