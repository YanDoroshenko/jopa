package cz.cvut.kbss.jopa.sessions.change;

import cz.cvut.kbss.jopa.environment.OWLClassA;
import cz.cvut.kbss.jopa.environment.utils.Generators;
import cz.cvut.kbss.jopa.environment.utils.MetamodelMocks;
import cz.cvut.kbss.jopa.model.metamodel.Metamodel;
import cz.cvut.kbss.jopa.sessions.MetamodelProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CollectionChangeDetectorTest {

    @Mock
    private MetamodelProvider providerMock;

    @Mock
    private Metamodel metamodel;

    private CollectionChangeDetector changeDetector;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final ChangeManagerImpl changeManager = new ChangeManagerImpl(providerMock);
        final MetamodelMocks mocks = new MetamodelMocks();
        mocks.setMocks(metamodel);
        when(providerMock.getMetamodel()).thenReturn(metamodel);
        this.changeDetector = new CollectionChangeDetector(new ChangeDetectors(providerMock, changeManager),
                changeManager, providerMock);
    }

    @Test
    public void hasChangesReturnsNoChangesForTwoSetsOfManagedClassInstances() {
        final Set<OWLClassA> original = initSet();
        final Set<OWLClassA> clone = initSet();
        when(providerMock.isTypeManaged(OWLClassA.class)).thenReturn(true);
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.FALSE, changed);
    }

    private Set<OWLClassA> initSet() {
        final List<OWLClassA> original = new ArrayList<>();
        final OWLClassA a1 = new OWLClassA(
                URI.create("http://onto.fel.cvut.cz/ontologies/documentation/question#instance-1523798300"));
        a1.setStringAttribute("one");
        original.add(a1);
        final OWLClassA a2 = new OWLClassA(
                URI.create("http://onto.fel.cvut.cz/ontologies/documentation/question#instance1483024927"));
        a2.setStringAttribute("two");
        original.add(a2);
        final OWLClassA a3 = new OWLClassA(
                URI.create("http://onto.fel.cvut.cz/ontologies/documentation/question#instance-2120907524"));
        a3.setStringAttribute("three");
        original.add(a3);
        Collections.shuffle(original);
        return new HashSet<>(original);
    }

    @Test
    public void hasChangesReturnsChangeWhenCollectionOfManagedInstancesHasUpdatedElement() {
        final Set<OWLClassA> original = initSet();
        final Set<OWLClassA> clone = initSet();
        when(providerMock.isTypeManaged(OWLClassA.class)).thenReturn(true);
        clone.iterator().next().setStringAttribute("updated");
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.TRUE, changed);
    }

    @Test
    public void hasChangesReturnsFalseForTwoEmptyCollections() {
        final Set<String> original = new HashSet<>();
        final Set<String> clone = new HashSet<>();
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.FALSE, changed);
    }

    @Test
    public void hasChangesReturnsFalseForTwoIdenticalCollectionsOfNonManagedTypes() {
        final List<Integer> lst = Arrays
                .asList(Generators.randomInt(100), Generators.randomInt(200), Generators.randomInt(500),
                        Generators.randomInt(400), Generators.randomInt(300));
        final Set<Integer> original = new HashSet<>(lst);
        final Set<Integer> clone = new HashSet<>(lst);
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.FALSE, changed);
    }

    @Test
    public void hasChangesReturnsTrueWhenCollectionSizeChanges() {
        final Set<OWLClassA> original = initSet();
        final Set<OWLClassA> clone = initSet();
        final Iterator<OWLClassA> it = clone.iterator();
        it.next();
        it.remove();
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.TRUE, changed);
    }

    @Test
    public void hasChangesReturnsTrueWhenOrderedCollectionOfNonManagedInstancesChanges() {
        final List<String> original = new ArrayList<>();
        final List<String> clone = new ArrayList<>();
        final int cnt = Generators.randomPositiveInt(117);
        for (int i = 0; i < cnt; i++) {
            original.add(Generators.createIndividualIdentifier().toString());
            clone.add(Generators.createIndividualIdentifier().toString());
        }
        final Changed changed = changeDetector.hasChanges(clone, original);
        assertEquals(Changed.TRUE, changed);
    }
}