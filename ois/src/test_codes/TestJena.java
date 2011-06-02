package test_codes;
import java.io.InputStream;
import java.io.InputStreamReader;

import vocabulary.Movieontology2;

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import com.hp.hpl.jena.util.FileManager;


public class TestJena {

	static final String inputFileName = "test_movieontology.owl";

	public static void main(String[] args) {
        System.out.println("Tests\n");

        selectProperty();
        selectResource();
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

	private static void selectResource() {
		System.out.println("Selecting movies whose genre is Adventure");

		Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName
                    + " not found");
        }
        model.read(new InputStreamReader(in), "");
        Bag movies = model.createBag();
        StmtIterator iter = model.listStatements(
        		new SimpleSelector(Movieontology2.belongsToGenre, null, (RDFNode) null) {
			public boolean selects(Statement s) {
				System.out.println(s.getSubject());
				StmtIterator it=((ResourceImpl)s.getSubject()).listProperties();
				while(it.hasNext()) {
					StatementImpl stmt=(StatementImpl)it.next();
					System.out.println(stmt);
					System.out.println(stmt.getSubject());
					System.out.println(stmt.getObject());
					System.out.println((ResourceImpl)stmt.getObject());
				}
//				ResourceImpl r=(ResourceImpl)s.getObject();
//				System.out.println(r);
//				StmtIterator it=r.listProperties();
//				System.out.println("p");
//				while(it.hasNext()) {
//					System.out.println(it.next());
//				}
//				System.out.println("/p");
				return true;
			}
		});
        while (iter.hasNext()) {
            movies.add(iter.nextStatement().getSubject());
        }
//        NodeIterator iter2 = movies.iterator();
//        if (iter2.hasNext()) {
//            while (iter2.hasNext()) {
//                System.out.println("  "
//                        + ((Resource) iter2.next())
//                        		.getRequiredProperty(Movieontology2.title)
//                                .getString()
//                                );
//            }
//        } else {
//            System.out.println("None");
//        }
	}

}
