package cz.cvut.kbss.jopa.oom;

import cz.cvut.kbss.jopa.environment.*;
import cz.cvut.kbss.jopa.environment.utils.Generators;
import cz.cvut.kbss.jopa.environment.utils.TestEnvironmentUtils;
import cz.cvut.kbss.jopa.exceptions.CardinalityConstraintViolatedException;
import cz.cvut.kbss.jopa.exceptions.IntegrityConstraintViolatedException;
import cz.cvut.kbss.jopa.model.SequencesVocabulary;
import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.jopa.model.descriptors.Descriptor;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import cz.cvut.kbss.jopa.model.metamodel.*;
import cz.cvut.kbss.jopa.owlapi.OWLAPIPersistenceProperties;
import cz.cvut.kbss.jopa.utils.Configuration;
import cz.cvut.kbss.ontodriver_new.descriptors.ReferencedListDescriptor;
import cz.cvut.kbss.ontodriver_new.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntityConstructorTest {

    private static final URI PK = URI.create("http://krizik.felk.cvut.cz/ontologies/jopa/entityX");
    private static final NamedResource PK_RESOURCE = NamedResource.create(PK);
    private static final URI PK_TWO = URI
            .create("http://krizik.felk.cvut.cz/ontologies/jopa/entityXX");
    private static final String STRING_ATT = "StringAttributeValue";
    private static final Set<String> TYPES = initTypes();

    @Mock
    private ObjectOntologyMapperImpl mapperMock;

    @Mock
    private EntityType<OWLClassA> etAMock;
    @Mock
    private Attribute strAttAMock;
    @Mock
    private TypesSpecification typesSpecMock;
    @Mock
    private Identifier idAMock;

    @Mock
    private EntityType<OWLClassB> etBMock;
    @Mock
    private Attribute strAttBMock;
    @Mock
    private PropertiesSpecification propsSpecMock;
    @Mock
    private Identifier idBMock;

    @Mock
    private EntityType<OWLClassD> etDMock;
    @Mock
    private Attribute clsAAttMock;
    @Mock
    private Identifier idDMock;

    @Mock
    private EntityType<OWLClassJ> etJMock;
    @Mock
    private PluralAttribute aSetAttMock;
    @Mock
    private Identifier idJMock;

    @Mock
    private EntityType<OWLClassL> etLMock;
    @Mock
    private ListAttribute simpleListAttMock;
    @Mock
    private ListAttribute refListAttMock;
    @Mock
    private PluralAttribute lSetAttMock;
    @Mock
    private SingularAttribute aAttMock;
    @Mock
    private Identifier idLMock;


    @Mock
    private EntityType<OWLClassM> etMMock;
    @Mock
    private SingularAttribute booleanAttMock;
    @Mock
    private SingularAttribute intAttMock;
    @Mock
    private SingularAttribute longAttMock;
    @Mock
    private SingularAttribute doubleAttMock;
    @Mock
    private SingularAttribute dateAttMock;
    @Mock
    private SingularAttribute enumAttMock;
    @Mock
    private Identifier idMMock;

    private Descriptor descriptor;

    private EntityConstructor constructor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mapperMock.getConfiguration()).thenReturn(new Configuration(Collections.emptyMap()));
        TestEnvironmentUtils.initOWLClassAMocks(etAMock, strAttAMock, typesSpecMock, idAMock);
        TestEnvironmentUtils.initOWLClassBMocks(etBMock, strAttBMock, propsSpecMock, idBMock);
        when(etBMock.getIdentifier()).thenReturn(idBMock);
        TestEnvironmentUtils.initOWLClassDMocks(etDMock, clsAAttMock, idDMock);
        TestEnvironmentUtils.initOWLClassJMocks(etJMock, aSetAttMock, idJMock);
        TestEnvironmentUtils.initOWLClassLMocks(etLMock, refListAttMock, simpleListAttMock, lSetAttMock, aAttMock,
                idLMock);
        TestEnvironmentUtils
                .initOWLClassMMock(etMMock, booleanAttMock, intAttMock, longAttMock, doubleAttMock, dateAttMock,
                        enumAttMock, idMMock);
        this.descriptor = new EntityDescriptor();
        this.constructor = new EntityConstructor(mapperMock);
    }

    private static Set<String> initTypes() {
        final Set<String> set = new HashSet<>(8);
        set.add("http://krizik.felk.cvut.cz/ontologies/jopa/entities#OWLClassU");
        set.add("http://krizik.felk.cvut.cz/ontologies/jopa/entities#OWLClassV");
        return set;
    }

    @Test
    public void testReconstructEntityWithTypesAndDataProperty() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassA.getClassIri()));
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        axioms.addAll(getTypesAxiomsForOwlClassA());
        final OWLClassA res = constructor.reconstructEntity(PK, etAMock, descriptor, axioms);
        assertNotNull(res);
        assertEquals(PK, res.getUri());
        assertEquals(STRING_ATT, res.getStringAttribute());
        assertEquals(TYPES, res.getTypes());
        verify(mapperMock).registerInstance(PK, res, descriptor.getContext());
    }

    private Axiom<URI> getClassAssertionAxiomForType(String type) {
        return new AxiomImpl<>(NamedResource.create(PK), Assertion.createClassAssertion(false),
                new Value<>(URI.create(type)));
    }

    private Axiom<String> getStringAttAssertionAxiom(Field attField) throws Exception {
        final String assertionIri = attField.getAnnotation(OWLDataProperty.class).iri();
        return new AxiomImpl<>(NamedResource.create(PK),
                Assertion.createDataPropertyAssertion(URI.create(assertionIri), false),
                new Value<>(STRING_ATT));
    }

    private Set<Axiom<?>> getTypesAxiomsForOwlClassA() throws Exception {
        final Set<Axiom<?>> axs = new HashSet<>();
        for (String type : TYPES) {
            final Axiom<URI> ax = new AxiomImpl<>(NamedResource.create(PK), Assertion.createClassAssertion(false),
                    new Value<>(URI.create(type)));
            axs.add(ax);
        }
        return axs;
    }

    @Test
    public void testReconstructEntityWithDataPropertyEmptyTypes() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassA.getClassIri()));
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        final OWLClassA res = constructor.reconstructEntity(PK, etAMock, descriptor, axioms);
        assertNotNull(res);
        assertEquals(PK, res.getUri());
        assertEquals(STRING_ATT, res.getStringAttribute());
        assertNull(res.getTypes());
        verify(mapperMock).registerInstance(PK, res, descriptor.getContext());
    }

    @Test
    public void testReconstructEntityWithDataPropertyAndProperties() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassB.getClassIri()));
        axioms.add(getStringAttAssertionAxiom(OWLClassB.getStrAttField()));
        final Collection<Axiom<?>> properties = getProperties();
        axioms.addAll(properties);
        final OWLClassB res = constructor.reconstructEntity(PK, etBMock, descriptor, axioms);
        assertNotNull(res);
        assertEquals(PK, res.getUri());
        assertEquals(STRING_ATT, res.getStringAttribute());
        assertNotNull(res.getProperties());
        assertEquals(properties.size(), res.getProperties().size());
        for (Axiom<?> a : properties) {
            final String key = a.getAssertion().getIdentifier().toString();
            assertTrue(res.getProperties().containsKey(key));
            assertEquals(1, res.getProperties().get(key).size());
            assertEquals(a.getValue().stringValue(), res.getProperties().get(key).iterator().next());
        }
        verify(mapperMock).registerInstance(PK, res, descriptor.getContext());
    }

    private Collection<Axiom<?>> getProperties() {
        final Set<Axiom<?>> props = new HashSet<>();

        final Axiom<String> axOne = new AxiomImpl<>(PK_RESOURCE, Assertion
                .createDataPropertyAssertion(URI.create("http://someDataPropertyOne"),
                        false), new Value<>("SomePropertyValue"));
        props.add(axOne);

        final Axiom<String> axTwo = new AxiomImpl<>(PK_RESOURCE, Assertion.createAnnotationPropertyAssertion(
                URI.create("http://someAnnotationPropertyOne"), false), new Value<>("annotationValue"));
        props.add(axTwo);

        final Axiom<URI> axThree = new AxiomImpl<>(PK_RESOURCE, Assertion
                .createObjectPropertyAssertion(URI.create("http://someObjectPropertyOne"),
                        false), new Value<>(URI.create("http://someObjectPropertyOne")));
        props.add(axThree);
        return props;
    }

    @Test
    public void testReconstructEntityWithDataPropertiesAndEmptyProperties() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassB.getClassIri()));
        axioms.add(getStringAttAssertionAxiom(OWLClassB.getStrAttField()));
        final OWLClassB res = constructor.reconstructEntity(PK, etBMock, descriptor, axioms);
        assertNotNull(res);
        assertEquals(PK, res.getUri());
        assertEquals(STRING_ATT, res.getStringAttribute());
        assertNull(res.getProperties());
        verify(mapperMock).registerInstance(PK, res, descriptor.getContext());
    }

    @Test
    public void testReconstructEntityWithObjectProperty() throws Exception {
        final Set<Axiom<?>> axiomsD = getAxiomsForD();
        final Descriptor fieldDesc = new EntityDescriptor();
        descriptor.addAttributeDescriptor(OWLClassD.getOwlClassAField(), fieldDesc);
        final OWLClassA entityA = new OWLClassA();
        entityA.setUri(PK_TWO);
        entityA.setStringAttribute(STRING_ATT);
        when(clsAAttMock.getFetchType()).thenReturn(FetchType.EAGER);
        when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDesc))
                .thenReturn(entityA);
        final OWLClassD res = constructor.reconstructEntity(PK, etDMock, descriptor, axiomsD);
        assertNotNull(res);
        assertEquals(PK, res.getUri());
        verify(mapperMock).getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDesc);
        assertNotNull(res.getOwlClassA());
        assertEquals(PK_TWO, res.getOwlClassA().getUri());
        assertEquals(STRING_ATT, res.getOwlClassA().getStringAttribute());
        verify(mapperMock).registerInstance(PK, res, descriptor.getContext());
    }

    private Set<Axiom<?>> getAxiomsForD() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassD.getClassIri()));
        final Axiom<NamedResource> opAssertion = createObjectPropertyAxiomForD();
        axioms.add(opAssertion);
        return axioms;
    }

    private Axiom<NamedResource> createObjectPropertyAxiomForD() throws NoSuchFieldException {
        return new AxiomImpl<>(NamedResource.create(PK), getClassDObjectPropertyAssertion(),
                new Value<>(NamedResource.create(PK_TWO)));
    }

    private Assertion getClassDObjectPropertyAssertion() throws NoSuchFieldException {
        final URI assertionUri = URI.create(OWLClassD.getOwlClassAField()
                                                     .getAnnotation(OWLObjectProperty.class).iri());
        return Assertion.createObjectPropertyAssertion(assertionUri, false);
    }

    @Test
    public void reconstructsEntityWithDataPropertyWhereRangeDoesNotMatch() throws Exception {
        final List<Axiom<?>> axioms = new ArrayList<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassA.getClassIri()));
        // Using a list and adding the incorrect range value before the right value will cause it to be processed first
        axioms.add(createDataPropertyAxiomWithWrongRange(OWLClassA.getStrAttField()));
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        OWLClassA entityA = constructor.reconstructEntity(PK, etAMock, descriptor, axioms);
        assertNotNull(entityA);
        assertEquals(STRING_ATT, entityA.getStringAttribute());

        axioms.clear();
        axioms.add(getClassAssertionAxiomForType(OWLClassA.getClassIri()));
        // Now reverse it
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        axioms.add(createDataPropertyAxiomWithWrongRange(OWLClassA.getStrAttField()));
        entityA = constructor.reconstructEntity(PK, etAMock, descriptor, axioms);
        assertNotNull(entityA);
        assertEquals(STRING_ATT, entityA.getStringAttribute());
    }

    @Test(expected = CardinalityConstraintViolatedException.class)
    public void throwsExceptionWhenDataPropertyCardinalityRestrictionIsNotMet() throws Exception {
        final List<Axiom<?>> axioms = new ArrayList<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassA.getClassIri()));
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        OWLClassA entityA = constructor.reconstructEntity(PK, etAMock, descriptor, axioms);
        // This shouldn't be reached
        assert entityA == null;
    }

    private Axiom<?> createDataPropertyAxiomWithWrongRange(Field valueField) throws Exception {
        final OWLDataProperty prop = valueField.getAnnotation(OWLDataProperty.class);
        assert prop != null;
        final Assertion assertion = Assertion.createDataPropertyAssertion(URI.create(prop.iri()), false);
        return new AxiomImpl<>(NamedResource.create(PK), assertion, new Value<>(System.currentTimeMillis()));
    }

    @Test
    public void reconstructsEntityWithObjectPropertyWhereRangeDoesNotMatch() throws Exception {
        final Set<Axiom<?>> axioms = getAxiomsForD();
        final Descriptor fieldDescriptor = new EntityDescriptor();
        descriptor.addAttributeDescriptor(OWLClassD.getOwlClassAField(), fieldDescriptor);
        // The property value hasn't the corresponding type, so it cannot be returned by mapper
        when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDescriptor)).thenReturn(null);
        final OWLClassD res = constructor.reconstructEntity(PK, etDMock, descriptor, axioms);
        assertNotNull(res);
        assertNull(res.getOwlClassA());
    }

    @Test(expected = CardinalityConstraintViolatedException.class)
    public void throwsExceptionWhenObjectPropertyCardinalityRestrictionIsNotMet() throws Exception {
        final Set<Axiom<?>> axioms = getAxiomsForD();
        final OWLClassA entityA = new OWLClassA();
        entityA.setUri(PK_TWO);
        final URI pkThree = URI.create("http://krizik.felk.cvut.cz/ontologies/jopa/entity3");
        axioms.add(new AxiomImpl<>(NamedResource.create(PK), getClassDObjectPropertyAssertion(),
                new Value<>(NamedResource.create(pkThree))));
        final OWLClassA entityThree = new OWLClassA();
        entityThree.setUri(pkThree);
        final Descriptor fieldDescriptor = new EntityDescriptor();
        descriptor.addAttributeDescriptor(OWLClassD.getOwlClassAField(), fieldDescriptor);
        when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDescriptor)).thenReturn(entityA);
        when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, pkThree, fieldDescriptor))
                .thenReturn(entityThree);
        final OWLClassD res = constructor.reconstructEntity(PK, etDMock, descriptor, axioms);
        // This shouldn't be reached
        assert res == null;
    }

    @Test
    public void testSetFieldValue_DataProperty() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getStringAttAssertionAxiom(OWLClassA.getStrAttField()));
        final OWLClassA entityA = new OWLClassA();
        entityA.setUri(PK);
        assertNull(entityA.getStringAttribute());
        constructor.setFieldValue(entityA, OWLClassA.getStrAttField(), axioms, etAMock, descriptor);
        assertNotNull(entityA.getStringAttribute());
        assertEquals(STRING_ATT, entityA.getStringAttribute());
    }

    @Test
    public void testSetFieldValue_ObjectProperty() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        final Descriptor fieldDesc = mock(Descriptor.class);
        descriptor.addAttributeDescriptor(OWLClassD.getOwlClassAField(), fieldDesc);
        axioms.add(createObjectPropertyAxiomForD());
        final OWLClassD entityD = new OWLClassD();
        entityD.setUri(PK);
        assertNull(entityD.getOwlClassA());
        final OWLClassA entityA = new OWLClassA();
        entityA.setUri(PK_TWO);
        entityA.setStringAttribute(STRING_ATT);
        when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDesc))
                .thenReturn(entityA);
        constructor.setFieldValue(entityD, OWLClassD.getOwlClassAField(), axioms, etDMock,
                descriptor);
        assertNotNull(entityD.getOwlClassA());
        assertEquals(PK_TWO, entityD.getOwlClassA().getUri());
        verify(mapperMock).getEntityFromCacheOrOntology(OWLClassA.class, PK_TWO, fieldDesc);
    }

    @Test
    public void testSetFieldValue_Types() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>(getTypesAxiomsForOwlClassA());
        final Descriptor fieldDesc = mock(Descriptor.class);
        descriptor.addAttributeDescriptor(OWLClassA.getTypesField(), fieldDesc);
        final OWLClassA entityA = new OWLClassA();
        entityA.setUri(PK);
        assertNull(entityA.getTypes());
        constructor.setFieldValue(entityA, OWLClassA.getTypesField(), axioms, etAMock, descriptor);
        assertNotNull(entityA.getTypes());
        assertEquals(TYPES.size(), entityA.getTypes().size());
        assertTrue(entityA.getTypes().containsAll(TYPES));
    }

    @Test
    public void testSetFieldValue_Properties() throws Exception {
        final Map<String, Set<String>> props = Generators.createProperties();
        final Set<Axiom<?>> axioms = initAxiomsForProperties(props);
        final OWLClassB entityB = new OWLClassB();
        entityB.setUri(PK);
        assertNull(entityB.getProperties());
        constructor.setFieldValue(entityB, OWLClassB.getPropertiesField(), axioms, etBMock,
                descriptor);
        assertNotNull(entityB.getProperties());
        assertEquals(props, entityB.getProperties());
    }

    private Set<Axiom<?>> initAxiomsForProperties(Map<String, Set<String>> props) {
        final Set<Axiom<?>> axioms = new HashSet<>();
        for (Entry<String, Set<String>> e : props.entrySet()) {
            final URI property = URI.create(e.getKey());
            axioms.addAll(e.getValue().stream().map(val -> new AxiomImpl<>(PK_RESOURCE, Assertion
                    .createPropertyAssertion(property, false), new Value<>(URI.create(val))))
                           .collect(Collectors.toList()));
        }
        return axioms;
    }

    @Test
    public void testSetFieldValue_ObjectPropertySet() throws Exception {
        final Descriptor desc = mock(Descriptor.class);
        final Set<OWLClassA> set = initEntities(desc);
        descriptor.addAttributeDescriptor(OWLClassJ.getOwlClassAField(), desc);
        final Collection<Axiom<?>> axioms = initAxiomsForReferencedSet(set);
        final OWLClassJ entityJ = new OWLClassJ();
        entityJ.setUri(PK);
        assertNull(entityJ.getOwlClassA());
        constructor.setFieldValue(entityJ, OWLClassJ.getOwlClassAField(), axioms, etJMock,
                descriptor);
        assertNotNull(entityJ.getOwlClassA());
        assertEquals(set.size(), entityJ.getOwlClassA().size());
        assertTrue(areEqual(set, entityJ.getOwlClassA()));
    }

    private Set<OWLClassA> initEntities(Descriptor desc) {
        final Set<OWLClassA> ents = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            final OWLClassA a = new OWLClassA();
            a.setUri(URI.create("http://krizik.felk.cvut.cz/entityA" + i));
            a.setStringAttribute(STRING_ATT);
            ents.add(a);
            when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, a.getUri(), desc))
                    .thenReturn(a);
        }
        return ents;
    }

    private Collection<Axiom<?>> initAxiomsForReferencedSet(Set<OWLClassA> as) throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        final URI property = URI.create(OWLClassJ.getOwlClassAField()
                                                 .getAnnotation(OWLObjectProperty.class).iri());
        for (OWLClassA a : as) {
            final Axiom<NamedResource> ax = new AxiomImpl<>(NamedResource.create(PK),
                    Assertion.createObjectPropertyAssertion(property, false),
                    new Value<>(NamedResource.create(
                            a.getUri())));
            axioms.add(ax);
        }
        return axioms;
    }

    private static boolean areEqual(Set<OWLClassA> expected, Set<OWLClassA> actual) {
        boolean found = false;
        for (OWLClassA a : expected) {
            found = false;
            for (OWLClassA aa : actual) {
                if (a.getUri().equals(aa.getUri())) {
                    found = true;
                    assertTrue(a.getStringAttribute().equals(aa.getStringAttribute()));
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return found;
    }

    @Test
    public void reconstructsEntityWithDataPropertiesOfBasicTypesAndStringIdentifier() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassM.getClassIri()));
        final Integer i = 117;
        final Long lng = 365L;
        final Double d = 3.14;
        final Date date = new Date();
        axioms.addAll(createAxiomsForValues(true, i, lng, d, date, null));

        final OWLClassM res = constructor.reconstructEntity(PK, etMMock, descriptor, axioms);
        assertEquals(true, res.getBooleanAttribute());
        assertEquals(i, res.getIntAttribute());
        assertEquals(lng, res.getLongAttribute());
        assertEquals(d, res.getDoubleAttribute());
        assertEquals(date, res.getDateAttribute());
    }

    private Collection<Axiom<?>> createAxiomsForValues(Boolean b, Integer i, Long lng, Double d, Date date,
                                                       OWLClassM.Severity severity) throws
            Exception {
        final Collection<Axiom<?>> axioms = new ArrayList<>();
        if (b != null) {
            final String boolAttIri = OWLClassM.getBooleanAttributeField().getAnnotation(OWLDataProperty.class).iri();
            axioms.add(
                    new AxiomImpl<>(PK_RESOURCE, Assertion.createDataPropertyAssertion(URI.create(boolAttIri), false),
                            new Value<>(b)));
        }
        if (i != null) {
            final String intAttIri = OWLClassM.getIntAttributeField().getAnnotation(OWLDataProperty.class).iri();
            axioms.add(new AxiomImpl<>(PK_RESOURCE, Assertion.createDataPropertyAssertion(URI.create(intAttIri), false),
                    new Value<>(i)));
        }
        if (lng != null) {
            final String longAttIri = OWLClassM.getLongAttributeField().getAnnotation(OWLDataProperty.class).iri();
            final Assertion laAssertion = Assertion.createDataPropertyAssertion(URI.create(longAttIri), false);
            axioms.add(new AxiomImpl<>(PK_RESOURCE, laAssertion, new Value<>(lng)));
        }
        if (d != null) {
            final String doubleAttIri = OWLClassM.getDoubleAttributeField().getAnnotation(OWLDataProperty.class).iri();
            axioms.add(
                    new AxiomImpl<>(PK_RESOURCE, Assertion.createDataPropertyAssertion(URI.create(doubleAttIri), false),
                            new Value<>(d)));
        }
        if (date != null) {
            final String dateAttIri = OWLClassM.getDateAttributeField().getAnnotation(OWLDataProperty.class).iri();
            axioms.add(
                    new AxiomImpl<>(PK_RESOURCE, Assertion.createDataPropertyAssertion(URI.create(dateAttIri), false),
                            new Value<>(date)));
        }
        if (severity != null) {
            final String enumAttIri = OWLClassM.getEnumAttributeField().getAnnotation(OWLDataProperty.class).iri();
            axioms.add(
                    new AxiomImpl<>(PK_RESOURCE, Assertion.createDataPropertyAssertion(URI.create(enumAttIri), false),
                            new Value<>(severity.toString())));
        }
        return axioms;
    }

    @Test
    public void reconstructsEntityWithEnumDataProperty() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassM.getClassIri()));
        final OWLClassM.Severity enumValue = OWLClassM.Severity.HIGH;
        axioms.addAll(createAxiomsForValues(null, null, null, null, null, enumValue));

        final OWLClassM result = constructor.reconstructEntity(PK, etMMock, descriptor, axioms);
        assertNotNull(result);
        assertNotNull(result.getEnumAttribute());
        assertEquals(enumValue, result.getEnumAttribute());
    }

    @Test(expected = IntegrityConstraintViolatedException.class)
    public void icsAreValidatedForAllFieldsOnEntityLoad() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassL.getClassIri()));

        constructor.reconstructEntity(PK, etLMock, descriptor, axioms);

    }

    @Test
    public void icValidationIsSkippedOnLoadBasedOnProperties() throws Exception {
        final Set<Axiom<?>> axioms = new HashSet<>();
        axioms.add(getClassAssertionAxiomForType(OWLClassL.getClassIri()));

        final Map<String, String> props = Collections
                .singletonMap(OWLAPIPersistenceProperties.DISABLE_IC_VALIDATION_ON_LOAD, Boolean.TRUE.toString());
        final Configuration conf = new Configuration(props);
        when(mapperMock.getConfiguration()).thenReturn(conf);

        final OWLClassL result = constructor.reconstructEntity(PK, etLMock, descriptor, axioms);
        assertNotNull(result);
        assertNull(result.getSingleA());
    }

    @Test(expected = IntegrityConstraintViolatedException.class)
    public void icsAreValidatedOnLazilyLoadedFieldFetch() throws Exception {
        final Set<Axiom<?>> fieldAxiom = initInvalidFieldValuesForICValidation();

        final OWLClassL instance = new OWLClassL();
        constructor.setFieldValue(instance, OWLClassL.getReferencedListField(), fieldAxiom, etLMock, descriptor);
    }

    private Set<Axiom<?>> initInvalidFieldValuesForICValidation() throws NoSuchFieldException {
        final Set<Axiom<NamedResource>> listAxioms = new HashSet<>();
        when(mapperMock.loadReferencedList(any(ReferencedListDescriptor.class))).thenReturn(listAxioms);
        final Descriptor attDescriptor = new EntityDescriptor();
        descriptor.addAttributeDescriptor(OWLClassL.getReferencedListField(), attDescriptor);
        final Assertion nodeContent = Assertion
                .createObjectPropertyAssertion(URI.create(SequencesVocabulary.s_p_hasContents), false);
        final int max = OWLClassL.getReferencedListField().getAnnotation(ParticipationConstraints.class).value()[0]
                .max();
        for (int i = 0; i < max + 1; i++) {
            final URI uri = URI.create("http://value" + i);
            listAxioms.add(new AxiomImpl<>(PK_RESOURCE, nodeContent, new Value<>(NamedResource.create(uri))));
            when(mapperMock.getEntityFromCacheOrOntology(OWLClassA.class, uri, attDescriptor))
                    .thenReturn(new OWLClassA(uri));
        }
        final Assertion assertion = Assertion.createObjectPropertyAssertion(
                URI.create(OWLClassL.getReferencedListField().getAnnotation(OWLObjectProperty.class).iri()), false);
        return Collections.singleton(
                new AxiomImpl<>(PK_RESOURCE, assertion, new Value<>(URI.create("http://referencedList"))));
    }

    @Test
    public void icsAreNotValidatedOnLazilyLoadedFetchWhenLoadingICValidationIsDisabled() throws Exception {
        final Set<Axiom<?>> fieldAxiom = initInvalidFieldValuesForICValidation();

        final Map<String, String> props = Collections
                .singletonMap(OWLAPIPersistenceProperties.DISABLE_IC_VALIDATION_ON_LOAD, Boolean.TRUE.toString());
        final Configuration conf = new Configuration(props);
        when(mapperMock.getConfiguration()).thenReturn(conf);

        final OWLClassL instance = new OWLClassL();
        constructor.setFieldValue(instance, OWLClassL.getReferencedListField(), fieldAxiom, etLMock, descriptor);
    }
}
