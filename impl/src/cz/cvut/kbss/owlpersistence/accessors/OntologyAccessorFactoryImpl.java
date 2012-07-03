package cz.cvut.kbss.owlpersistence.accessors;

import java.util.Map;

import cz.cvut.kbss.owlpersistence.model.metamodel.Metamodel;
import cz.cvut.kbss.owlpersistence.owlapi.OWLAPIPersistenceProperties;
import cz.cvut.kbss.owlpersistence.sessions.Session;

public class OntologyAccessorFactoryImpl implements OntologyAccessorFactory {

	public OntologyAccessor createOntologyAccessor(
			Map<String, String> properties, Metamodel metamodel, Session session) {
		final String ontologyLocation = properties
				.get(OWLAPIPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY);
		if (ontologyLocation.startsWith("jdbc")) {
			return new OWLDBOntologyAccessor(properties, metamodel, session);
		} else {
			return new OWLFileOntologyAccessor(properties, metamodel, session);
		}
		// If new types of ontology access are added (besides OWLAPI), this
		// method will need to be refactored to fit the new strategy
	}

}
