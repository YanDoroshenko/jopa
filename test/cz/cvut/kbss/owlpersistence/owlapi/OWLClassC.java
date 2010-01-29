package cz.cvut.kbss.owlpersistence.owlapi;

import java.net.URI;
import java.util.List;

import cz.cvut.kbss.owlpersistence.model.annotations.Id;
import cz.cvut.kbss.owlpersistence.model.annotations.OWLClass;
import cz.cvut.kbss.owlpersistence.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.owlpersistence.model.annotations.OWLSequence;
import cz.cvut.kbss.owlpersistence.model.annotations.OWLSequenceType;

@OWLClass(iri = "http://OWLClassC")
public class OWLClassC {

	@Id
	private URI uri;

	@OWLSequence
	@OWLObjectProperty(iri = "http://B-hasReferencedSequence")
	private List<OWLClassA> referencedList;

	@OWLSequence(type = OWLSequenceType.simple, ObjectPropertyHasNextIRI = "http://B-hasSimpleNext")
	@OWLObjectProperty(iri = "http://B-hasSimpleSequence")
	private List<OWLClassA> simplelist;

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}

	public void setReferencedList(List<OWLClassA> list) {
		this.referencedList = list;
	}

	public List<OWLClassA> getReferencedList() {
		return referencedList;
	}

	public void setSimpleList(List<OWLClassA> simplelist) {
		this.simplelist = simplelist;
	}

	public List<OWLClassA> getSimpleList() {
		return simplelist;
	}

}