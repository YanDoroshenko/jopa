/**
 * Copyright (C) 2016 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.ontodriver.owlapi;

import cz.cvut.kbss.ontodriver.model.*;
import cz.cvut.kbss.ontodriver.owlapi.util.OwlapiUtils;
import org.semanticweb.owlapi.model.*;

/**
 * Adapts OWLAPI axioms to JOPA (OntoDriver) axioms and vice versa.
 */
public class AxiomAdapter {

    private final OWLDataFactory dataFactory;
    private final String language;

    public AxiomAdapter(OWLDataFactory dataFactory, String language) {
        this.dataFactory = dataFactory;
        this.language = language;
    }

    OWLAxiom toOwlClassAssertionAxiom(Axiom<?> axiom) {
        final OWLClass owlClass = dataFactory.getOWLClass(IRI.create(axiom.getValue().stringValue()));
        return dataFactory.getOWLClassAssertionAxiom(owlClass, toOWLIndividual(axiom.getSubject()));
    }

    public OWLAxiom toOwlObjectPropertyAssertionAxiom(Axiom<?> axiom) {
        final OWLObjectProperty objectProperty = dataFactory
                .getOWLObjectProperty(IRI.create(axiom.getAssertion().getIdentifier()));
        final OWLNamedIndividual objectValue = dataFactory.getOWLNamedIndividual(
                IRI.create(axiom.getValue().stringValue()));
        return dataFactory.getOWLObjectPropertyAssertionAxiom(objectProperty, toOWLIndividual(axiom.getSubject()),
                objectValue);
    }

    OWLAxiom toOwlDataPropertyAssertionAxiom(Axiom<?> axiom) {
        final OWLDataProperty dataProperty = dataFactory
                .getOWLDataProperty(IRI.create(axiom.getAssertion().getIdentifier()));
        final OWLLiteral dataValue = OwlapiUtils.createOWLLiteralFromValue(axiom.getValue().getValue(),
                dataFactory, language(axiom.getAssertion()));
        return dataFactory
                .getOWLDataPropertyAssertionAxiom(dataProperty, toOWLIndividual(axiom.getSubject()), dataValue);
    }

    private String language(Assertion assertion) {
        return assertion.hasLanguage() ? assertion.getLanguage() : language;
    }

    OWLAxiom toOwlAnnotationPropertyAssertionAxiom(Axiom<?> axiom) {
        final OWLAnnotationProperty annotationProperty = dataFactory.getOWLAnnotationProperty(IRI.create(
                axiom.getAssertion().getIdentifier()));
        final Object value = axiom.getValue().getValue();
        final OWLAnnotationValue annotationValue;
        if (OwlapiUtils.isIndividualIri(value)) {
            annotationValue = IRI.create(value.toString());
        } else {
            annotationValue = OwlapiUtils.createOWLLiteralFromValue(
                    axiom.getValue().getValue(), dataFactory, language(axiom.getAssertion()));
        }
        return dataFactory
                .getOWLAnnotationAssertionAxiom(annotationProperty, toOWLIndividual(axiom.getSubject()).getIRI(),
                        annotationValue);
    }

    private OWLNamedIndividual toOWLIndividual(NamedResource subject) {
        return dataFactory.getOWLNamedIndividual(IRI.create(subject.getIdentifier()));
    }

    Axiom<?> toAxiom(NamedResource subject, OWLDataPropertyAssertionAxiom assertionAxiom, boolean isInferred) {
        final Assertion assertion = Assertion
                .createDataPropertyAssertion(assertionAxiom.getProperty().asOWLDataProperty().getIRI().toURI(),
                        isInferred);
        return createAxiom(subject, assertion, OwlapiUtils.owlLiteralToValue(assertionAxiom.getObject()));
    }

    public <V> Axiom<V> createAxiom(NamedResource subject, Assertion assertion, V value) {
        return new AxiomImpl<>(subject, assertion, new Value<>(value));
    }

    Axiom<?> toAxiom(NamedResource subject, OWLObjectPropertyAssertionAxiom assertionAxiom, boolean isInferred) {
        final Assertion assertion = Assertion
                .createObjectPropertyAssertion(assertionAxiom.getProperty().asOWLObjectProperty().getIRI().toURI(),
                        isInferred);
        final IRI target = assertionAxiom.getObject().asOWLNamedIndividual().getIRI();
        return createAxiom(subject, assertion, NamedResource.create(target.toURI()));
    }

    Axiom<?> toAxiom(NamedResource subject, OWLAnnotationAssertionAxiom assertionAxiom, boolean isInferred) {
        final Assertion assertion = Assertion
                .createAnnotationPropertyAssertion(
                        assertionAxiom.getProperty().asOWLAnnotationProperty().getIRI().toURI(),
                        isInferred);
        if (assertionAxiom.getValue().asIRI().isPresent()) {
            return createAxiom(subject, assertion, assertionAxiom.getValue().asIRI().get().toURI());
        } else {
            return createAxiom(subject, assertion, OwlapiUtils.owlLiteralToValue(
                    assertionAxiom.getValue().asLiteral().get()));
        }
    }
}
