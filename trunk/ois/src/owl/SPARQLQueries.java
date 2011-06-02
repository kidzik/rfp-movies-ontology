package owl;

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

	static final String inputFile = "owl/movie_ontology.owl";

	public static void main(String[] args) {

		OntModel model = loadModel(inputFile);

		// Give me all titles
		System.out.println("Give me all titles:");
		String qAllTitles = "SELECT ?p ?o WHERE {?p <http://www.movieontology.org/2009/10/01/movieontology.owl#title> ?o."
				+ "}";
		submitQuery(model, qAllTitles);

		// Give me all titles containing "the"
		System.out.println("Give me all titles containing \"the\":");
		String qThe = "SELECT ?p ?genre WHERE {?p <http://www.movieontology.org/2009/10/01/movieontology.owl#title> ?o."
				+ "  FILTER regex(?o, \"the\", \"i\") " + "}";
		submitQuery(model, qThe);

		// Give me 1 action movie from last year (2010)
		System.out.println("Give me 1 action movie from last year (2010):");
		String qActionLastYear = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?p ?date WHERE {?p <http://www.movieontology.org/2009/10/01/movieontology.owl#belongsToGenre> <http://www.movieontology.org/2009/10/01/movieontology.owl#Action>."
				+ "?p <http://www.movieontology.org/2009/10/01/movieontology.owl#releasedate> ?date."
				+ "FILTER (?date >= \"2010-01-01\"^^xsd:date)."
				+ "} LIMIT 1";
		submitQuery(model, qActionLastYear);

		// Give me 2 comedy movies with Jim Carrey
		System.out.println("Give me 2 comedy movies with Jim Carrey:");
		String qComedyJimCarrey = "SELECT ?p WHERE {?p <http://www.movieontology.org/2009/10/01/movieontology.owl#belongsToGenre> <http://www.movieontology.org/2009/10/01/movieontology.owl#Comedy>."
				+ "?p <http://www.movieontology.org/2009/10/01/movieontology.owl#hasActor> <http://dbpedia.org/resource/Jim_Carrey>."
				+ "} LIMIT 2";
		submitQuery(model, qComedyJimCarrey);
		
//		// Give me 1 movie with IMDB rating greater than 8
//		System.out.println("Give me 1 movie with IMDB rating greater than 8");
//		String qIMDB8 = "SELECT ?p ?imdbrating WHERE {?p <http://www.movieontology.org/2009/10/01/movieontology.owl#imdbrating> ?imdbrating."
//				+ "FILTER (?imdbrating > 2.0)."
//				+ "} LIMIT 1";
//		submitQuery(model, qIMDB8);
//		
		
	}

	private static void submitQuery(OntModel model, String query) {
		System.out.println(query);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

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
		System.out.println();
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
