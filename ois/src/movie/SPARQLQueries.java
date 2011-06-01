package movie;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class SPARQLQueries {

	static final String inputFile = "movie_ontology.owl";

	public static void main(String[] args) {
		
		OntModel model = loadModel(inputFile);
		
		// example of query
		String query = "SELECT ?p ?o WHERE {?p <http://localhost/WTFDL/movie_ontology.owl#title> ?o." +
				"}";
		String query2 = "SELECT ?p ?genre WHERE {?p <http://localhost/WTFDL/movie_ontology.owl#title> \"Lord of the Rings\"." +
									"?p <http://www.movieontology.org/2009/10/01/movieontology.owl#belongsToGenre> ?genre." +
									"}";

		
		//possible future queries
//		String query2 = "SELECT ?m WHERE {" +
//				"?genre <http://...#belongsToGenre> ?m." +
//				"?m <http://...#hasMaximumAgeRating> ?number." +
//				"FILTER {?number > 25}}";	
		
		submitQuery(model, query);
		submitQuery(model, query2);
	}

	private static void submitQuery(OntModel model, String query) {
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;

		ResultSet results = (ResultSet) qexec.execSelect();
		int i = 1;
		while (((com.hp.hpl.jena.query.ResultSet) results).hasNext()) {
			QuerySolution soln = ((com.hp.hpl.jena.query.ResultSet) results)
					.nextSolution();
			Iterator<String> varNames = soln.varNames();
			System.out.print(i++ + "\t");
			while (varNames.hasNext()) {
				System.out.print(soln.get(varNames.next()));
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static OntModel loadModel(String inputFileName) {
		// create an empty model
		OntModel model = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);

		// use the class loader to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		// read the RDF/XML/OWL file
		model.read(new InputStreamReader(in), "");

		return model;
	}
}
