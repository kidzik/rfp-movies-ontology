package test_codes;
import java.io.PrintWriter;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class PracRDFFirst {

	public static void main(String args[]) {
		String sURI = "http://burningbird.net/articles/monsters1.htm";
		String sPostcon = "http://www.burningbird.net/postcon/elements/1.0/";
		String sRelated = "related";
		try {
			// Create an empty graph
			Model model = ModelFactory.createDefaultModel();
			// Create the resource
			Resource postcon = model.createResource(sURI);
			// Create the predicate (property)
			Property related = model.createProperty(sPostcon, sRelated);
			// Add the properties with associated values (objects)
			postcon.addProperty(related,
					"http://burningbird.net/articles/monsters3.htm");
			postcon.addProperty(related,
					"http://burningbird.net/articles/monsters2.htm");
			// Print RDF/XML of model to system output
			model.write(new PrintWriter(System.out));
		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}
	}

}
