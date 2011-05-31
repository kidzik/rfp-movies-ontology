package test_codes;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

public class GenerateRDF extends Object {
	public static void main(String args[]) {
		String personURI = "http://localhost/amitkumar";
		String givenName = "Amit";
		String familyName = "Kumar";
		String fullName = givenName + familyName;

		//IMPORT RDF MODEL
		Model model = ModelFactory.createDefaultModel();

		//FEED SOME DATA
		Resource node = model.createResource(personURI).addProperty(VCARD.FN,
				fullName).addProperty(
				VCARD.N,
				model.createResource().addProperty(VCARD.Given, givenName)
						.addProperty(VCARD.Family, familyName));
		model.write(System.out);

		// Querying RDF model
		ResIterator iter = model.listSubjectsWithProperty(VCARD.N);
		while (iter.hasNext()) {
			Resource res = iter.nextResource();
			System.out.println("Property  "
					+ res.getProperty(VCARD.FN).getString());
			System.out.println("Resource URI  " + res.getURI());
		}
	}
}