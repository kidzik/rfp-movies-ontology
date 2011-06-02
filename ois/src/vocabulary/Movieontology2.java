package vocabulary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Movieontology2 {

    protected static final String uri ="http://www.movieontology.org/2009/10/01/movieontology.owl#";

    private static Model m = ModelFactory.createDefaultModel();

    public static final Property title = m.createProperty(uri, "title" );
    public static final Resource belongsToGenre = m.createResource(uri + "belongsToGenre" );

}
