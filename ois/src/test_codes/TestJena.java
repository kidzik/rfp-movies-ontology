package test_codes;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class TestJena {

	public static void main(String[] args) {
		OntModel ont = ModelFactory.createOntologyModel("http://www.w3.org/2000/01/rdf-schema#");
		
		
//		private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		private OWLOntology ontology = manager.createOntology(IRI.create("file://" + file.getAbsolutePath()));
//		private PrefixManager pm = new DefaultPrefixManager("file://" + file.getAbsolutePath() + "#");
//		private OWLDataFactory factory = manager.getOWLDataFactory();
	}

}
