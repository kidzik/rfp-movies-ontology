package owl;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/** Tutorial 10 - demonstrate a container
 *
 * @author  bwm - updated by kers/Daniel
 * @version Release='$Name:  $' Revision='$Revision: 1.3 $' Date='$Date: 2005/10/06 17:49:05 $'
 */
public class AccessModel extends Object {

    static final String inputFileName = "test_movieontology.owl"; //"vc-db-1.rdf";

    public static void main(String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        
        // use the class loader to find the input file
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName
                    + " not found");
        }

        // read the RDF/XML/OWL file
        model.read(new InputStreamReader(in), "");

        // create a bag
        Bag movies = model.createBag();
        
        Model model2 = ModelFactory.createDefaultModel();
        Property pTitle = model2.createProperty(model.getNsPrefixURI(""),"title");
        Property pAdventure = model2.createProperty(model.getNsPrefixURI(""),"Adventure");
        Resource rMovie = model2.createResource(model.getNsPrefixURI("") + "Genre");
        
//        StmtIterator iter = model.listStatements(
//        		new SimpleSelector(null, pTitle, (RDFNode) null) {
//			public boolean selects(Statement s) {
//				return true;
//			}
//		});
        System.out.println(model.getNsPrefixURI(""));
        
        StmtIterator iter = model.listStatements(
        		new SimpleSelector(rMovie, pAdventure, (RDFNode) null) {
			public boolean selects(Statement s) {
				return true;
			}
		});
        // add the Smith's to the bag
        while (iter.hasNext()) {
            movies.add(iter.nextStatement().getSubject());
        }

        // print the graph as RDF/XML
//        model.write(new PrintWriter(System.out));
        System.out.println();

        // print out the members of the bag
        NodeIterator iter2 = movies.iterator();
        if (iter2.hasNext()) {
            System.out.println("The bag contains:");
            while (iter2.hasNext()) {
                System.out.println("  "
                        + ((Resource) iter2.next())//.toString()
                        		.getRequiredProperty(pAdventure)
                                .getString()
                                );
            }
        } else {
            System.out.println("The bag is empty");
        }
    }
}