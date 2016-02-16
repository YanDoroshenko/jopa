package cz.cvut.kbss.ontodriver.model;

import cz.cvut.kbss.ontodriver.util.Vocabulary;

import java.net.URI;

class ClassAssertion extends Assertion {

    private static final long serialVersionUID = 7238514047567604200L;

    static final URI RDF_TYPE = URI.create(Vocabulary.RDF_TYPE);

    ClassAssertion(boolean isInferred) {
        super(RDF_TYPE, isInferred);
    }

    @Override
    public AssertionType getType() {
        return AssertionType.CLASS;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }
}
