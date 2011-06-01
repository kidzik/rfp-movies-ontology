package test_codes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import vocabulary.Movieontology2;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;


public class TestJena {

	static final String inputFileName = "test_movieontology.owl";

	public static void main(String[] args) {
        System.out.println("Tests\n");

        selectProperty();
	}

	private static void selectProperty() {
		System.out.println("Selecting movies whose title contains Ring");

		Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName
                    + " not found");
        }
        model.read(new InputStreamReader(in), "");
        Bag movies = model.createBag();
        StmtIterator iter = model.listStatements(
        		new SimpleSelector(null, Movieontology2.title, (RDFNode) null) {
			public boolean selects(Statement s) {
				return s.getString().toLowerCase().contains("ring");
			}
		});
        while (iter.hasNext()) {
            movies.add(iter.nextStatement().getSubject());
        }
        NodeIterator iter2 = movies.iterator();
        if (iter2.hasNext()) {
            while (iter2.hasNext()) {
                System.out.println("  "
                        + ((Resource) iter2.next())
                        		.getRequiredProperty(Movieontology2.title)
                                .getString()
                                );
            }
        } else {
            System.out.println("None");
        }
	}

}
