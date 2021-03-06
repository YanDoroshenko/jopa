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
package cz.cvut.kbss.jopa.environment.utils;

import cz.cvut.kbss.jopa.environment.*;
import cz.cvut.kbss.jopa.environment.listener.AnotherListener;
import cz.cvut.kbss.jopa.environment.listener.ConcreteListener;
import cz.cvut.kbss.jopa.environment.listener.ParentListener;
import cz.cvut.kbss.jopa.model.metamodel.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Provides metamodel mock objects for the test classes.
 */
@SuppressWarnings("unused")
public class MetamodelMocks {

    @Mock
    private EntityTypeImpl<OWLClassA> etA;
    @Mock
    private Identifier idA;
    @Mock
    private SingularAttribute<OWLClassA, String> aStringAtt;
    @Mock
    private TypesSpecification<OWLClassA, ?> aTypes;

    @Mock
    private EntityTypeImpl<OWLClassB> etB;
    @Mock
    private Identifier idB;
    @Mock
    private SingularAttribute<OWLClassB, String> bStringAtt;
    @Mock
    private PropertiesSpecification<OWLClassB, Map, String, String> bProperties;

    @Mock
    private EntityTypeImpl<OWLClassC> etC;
    @Mock
    private Identifier idC;
    @Mock
    private ListAttribute<OWLClassC, OWLClassA> cReferencedList;
    @Mock
    private ListAttribute<OWLClassC, OWLClassA> cSimpleList;

    @Mock
    private EntityTypeImpl<OWLClassD> etD;
    @Mock
    private Identifier idD;
    @Mock
    private SingularAttribute<OWLClassD, OWLClassA> dOwlClassAAtt;

    @Mock
    private EntityTypeImpl<OWLClassE> etE;
    @Mock
    private Identifier idE;
    @Mock
    private SingularAttribute<OWLClassE, String> eStringAtt;

    @Mock
    private EntityTypeImpl<OWLClassG> etG;
    @Mock
    private Identifier idG;
    @Mock
    private SingularAttribute<OWLClassG, OWLClassH> gOwlClassHAtt;

    @Mock
    private EntityTypeImpl<OWLClassF> etF;
    @Mock
    private Identifier idF;
    @Mock
    private SingularAttribute<OWLClassF, String> fStringAtt;
    @Mock
    private PluralAttribute<OWLClassF, Set, OWLClassA> fSetAtt;

    @Mock
    private EntityTypeImpl<OWLClassH> etH;
    @Mock
    private Identifier idH;
    @Mock
    private SingularAttribute<OWLClassH, OWLClassA> hOwlClassAAtt;
    @Mock
    private SingularAttribute<OWLClassH, OWLClassG> hOwlClassGAtt;

    @Mock
    private EntityTypeImpl<OWLClassJ> etJ;
    @Mock
    private Identifier idJ;
    @Mock
    private PluralAttribute<OWLClassJ, Set, OWLClassA> jSetAtt;

    @Mock
    private EntityTypeImpl<OWLClassK> etK;
    @Mock
    private Identifier idK;
    @Mock
    private SingularAttribute<OWLClassK, OWLClassE> kOwlClassEAtt;

    @Mock
    private EntityTypeImpl<OWLClassL> etL;
    @Mock
    private Identifier idL;
    @Mock
    private ListAttribute<OWLClassL, OWLClassA> lSimpleList;
    @Mock
    private ListAttribute<OWLClassL, OWLClassA> lReferencedList;
    @Mock
    private PluralAttribute<OWLClassL, Set, OWLClassA> lSetAtt;
    @Mock
    private SingularAttribute<OWLClassL, OWLClassA> lOwlClassAAtt;

    @Mock
    private EntityTypeImpl<OWLClassM> etM;
    @Mock
    private Identifier idM;
    @Mock
    private SingularAttribute<OWLClassM, Boolean> mBooleanAtt;
    @Mock
    private SingularAttribute<OWLClassM, Integer> mIntegerAtt;
    @Mock
    private SingularAttribute<OWLClassM, Long> mLongAtt;
    @Mock
    private SingularAttribute<OWLClassM, Double> mDoubleAtt;
    @Mock
    private SingularAttribute<OWLClassM, Date> mDateAtt;
    @Mock
    private SingularAttribute<OWLClassM, OWLClassM.Severity> mEnumAtt;
    @Mock
    private PluralAttribute<OWLClassM, Set, Integer> mIntegerSetAtt;

    @Mock
    private EntityTypeImpl<OWLClassN> etN;
    @Mock
    private Identifier idN;
    @Mock
    private SingularAttribute<OWLClassN, String> nAnnotationAtt;
    @Mock
    private SingularAttribute<OWLClassN, URI> nAnnotationUriAtt;
    @Mock
    private SingularAttribute<OWLClassN, String> nStringAtt;
    @Mock
    private PropertiesSpecification<OWLClassN, Map, String, String> nProperties;

    @Mock
    private EntityTypeImpl<OWLClassO> etO;
    @Mock
    private Identifier idO;
    @Mock
    private SingularAttribute<OWLClassO, String> oStringAtt;

    @Mock
    private EntityTypeImpl<OWLClassP> etP;
    @Mock
    private Identifier idP;
    @Mock
    private SingularAttribute<OWLClassP, URI> pUriAtt;
    @Mock
    private PluralAttribute<OWLClassP, Set, URL> pUrlsAtt;
    @Mock
    private ListAttribute<OWLClassP, URI> pSimpleList;
    @Mock
    private ListAttribute<OWLClassP, URI> pReferencedList;
    @Mock
    private TypesSpecification<OWLClassP, URI> pTypes;
    @Mock
    private PropertiesSpecification<OWLClassP, Map, URI, Object> pProperties;

    @Mock
    private MappedSuperclassTypeImpl<QMappedSuperclass> qMappedSuperclass;
    @Mock
    private EntityTypeImpl<OWLClassQ> etQ;
    @Mock
    private Identifier idQ;
    @Mock
    private SingularAttribute<OWLClassQ, String> qStringAtt;
    @Mock
    private SingularAttribute<OWLClassQ, String> qLabelAtt;
    @Mock
    private SingularAttribute<OWLClassQ, String> qParentStringAtt;
    @Mock
    private SingularAttribute<OWLClassQ, OWLClassA> qOwlClassAAtt;

    @Mock
    private EntityTypeImpl<OWLClassR> etR;
    @Mock
    private SingularAttribute<OWLClassR, String> rStringAtt;
    @Mock
    private SingularAttribute<OWLClassR, OWLClassA> rOwlClassAAtt;
    @Mock
    private ConcreteListener concreteListenerMock;
    @Mock
    private AnotherListener anotherListenerMock;

    @Mock
    private EntityTypeImpl<OWLClassS> etS;
    @Mock
    private Identifier idS;
    @Mock
    private SingularAttribute<OWLClassS, String> sNameAtt;
    @Mock
    private TypesSpecification<OWLClassS, String> sTypes;
    @Mock
    private ParentListener parentListenerMock;

    public MetamodelMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        MetamodelFactory.initOWLClassAMocks(etA, aStringAtt, aTypes, idA);
        MetamodelFactory.initOWLClassBMocks(etB, bStringAtt, bProperties, idB);
        MetamodelFactory.initOWLClassCMocks(etC, cSimpleList, cReferencedList, idC);
        MetamodelFactory.initOWLClassDMocks(etD, dOwlClassAAtt, idD);
        MetamodelFactory.initOWLClassEMocks(etE, eStringAtt, idE);
        MetamodelFactory.initOWLClassFMocks(etF, fSetAtt, fStringAtt, idF);
        MetamodelFactory.iniOWLClassGMocks(etG, gOwlClassHAtt, idG);
        MetamodelFactory.initOWLClassHMocks(etH, hOwlClassAAtt, hOwlClassGAtt, idH);
        MetamodelFactory.initOWLClassJMocks(etJ, jSetAtt, idJ);
        MetamodelFactory.initOWLClassKMocks(etK, kOwlClassEAtt, idK);
        MetamodelFactory.initOWLClassLMocks(etL, lReferencedList, lSimpleList, lSetAtt, lOwlClassAAtt, idL);
        MetamodelFactory.initOWLClassMMock(etM, mBooleanAtt, mIntegerAtt, mLongAtt, mDoubleAtt, mDateAtt, mEnumAtt,
                mIntegerSetAtt, idM);
        MetamodelFactory.initOWLClassNMock(etN, nAnnotationAtt, nAnnotationUriAtt, nStringAtt, nProperties, idN);
        MetamodelFactory.initOWLClassOMock(etO, oStringAtt, idO);
        MetamodelFactory
                .initOWLClassPMock(etP, pTypes, pProperties, pUriAtt, pUrlsAtt, pSimpleList, pReferencedList, idP);
        MetamodelFactory
                .initOwlClassQMock(etQ, qMappedSuperclass, qStringAtt, qParentStringAtt, qLabelAtt, qOwlClassAAtt, idQ);
        MetamodelFactory.initOwlClassSMock(etS, sNameAtt, sTypes, idS);
        MetamodelFactory.initOwlClassSListeners(etS, parentListenerMock);
        MetamodelFactory.initOwlClassRMock(etR, rStringAtt, rOwlClassAAtt, etS);
        MetamodelFactory.initOwlClassRListeners(etR, etS, concreteListenerMock, anotherListenerMock);
    }

    public void setMocks(Metamodel metamodel) {
        // Order does matter for the entity() method implementation below
        final Map<Class<?>, EntityTypeImpl<?>> etMap = new LinkedHashMap<>();
        etMap.put(OWLClassA.class, etA);
        etMap.put(OWLClassB.class, etB);
        etMap.put(OWLClassC.class, etC);
        etMap.put(OWLClassD.class, etD);
        etMap.put(OWLClassE.class, etE);
        etMap.put(OWLClassF.class, etF);
        etMap.put(OWLClassG.class, etG);
        etMap.put(OWLClassH.class, etH);
        etMap.put(OWLClassJ.class, etJ);
        etMap.put(OWLClassK.class, etK);
        etMap.put(OWLClassL.class, etL);
        etMap.put(OWLClassM.class, etM);
        etMap.put(OWLClassN.class, etN);
        etMap.put(OWLClassO.class, etO);
        etMap.put(OWLClassP.class, etP);
        etMap.put(OWLClassQ.class, etQ);
        etMap.put(OWLClassR.class, etR);
        etMap.put(OWLClassS.class, etS);
        when(metamodel.entity(any())).thenAnswer(invocation -> {
            final Class<?> cls = (Class<?>) invocation.getArguments()[0];
            if (etMap.containsKey(cls)) {
                return etMap.get(cls);
            }
            for (Class<?> type : etMap.keySet()) {
                if (type.isAssignableFrom(cls)) {
                    return etMap.get(type);
                }
            }
            throw new IllegalArgumentException(
                    "Class " + cls.getName() + " is not a known entity in this persistence unit.");
        });
    }

    public OWLClassAMetamodel forOwlClassA() {
        return new OWLClassAMetamodel();
    }

    public OWLClassBMetamodel forOwlClassB() {
        return new OWLClassBMetamodel();
    }

    public OWLClassCMetamodel forOwlClassC() {
        return new OWLClassCMetamodel();
    }

    public OWLClassDMetamodel forOwlClassD() {
        return new OWLClassDMetamodel();
    }

    public OWLClassEMetamodel forOwlClassE() {
        return new OWLClassEMetamodel();
    }

    public OWLClassJMetamodel forOwlClassJ() {
        return new OWLClassJMetamodel();
    }

    public OWLClassKMetamodel forOwlClassK() {
        return new OWLClassKMetamodel();
    }

    public OWLClassLMetamodel forOwlClassL() {
        return new OWLClassLMetamodel();
    }

    public OWLClassMMetamodel forOwlClassM() {
        return new OWLClassMMetamodel();
    }

    public OWLClassNMetamodel forOwlClassN() {
        return new OWLClassNMetamodel();
    }

    public OWLClassOMetamodel forOwlClassO() {
        return new OWLClassOMetamodel();
    }

    public OWLClassPMetamodel forOwlClassP() {
        return new OWLClassPMetamodel();
    }

    public OWLClassQMetamodel forOwlClassQ() {
        return new OWLClassQMetamodel();
    }

    public OWLClassRMetamodel forOwlClassR() {
        return new OWLClassRMetamodel();
    }

    public OWLClassSMetamodel forOwlClassS() {
        return new OWLClassSMetamodel();
    }

    public class OWLClassAMetamodel {
        public EntityTypeImpl<OWLClassA> entityType() {
            return MetamodelMocks.this.etA;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idA;
        }

        public SingularAttribute<OWLClassA, String> stringAttribute() {
            return MetamodelMocks.this.aStringAtt;
        }

        public TypesSpecification<OWLClassA, ?> typesSpec() {
            return MetamodelMocks.this.aTypes;
        }
    }

    public class OWLClassBMetamodel {
        public EntityTypeImpl<OWLClassB> entityType() {
            return MetamodelMocks.this.etB;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idB;
        }

        public SingularAttribute<OWLClassB, String> stringAttribute() {
            return MetamodelMocks.this.bStringAtt;
        }

        public PropertiesSpecification<OWLClassB, Map, String, String> propertiesSpec() {
            return MetamodelMocks.this.bProperties;
        }
    }

    public class OWLClassCMetamodel {
        public EntityTypeImpl<OWLClassC> entityType() {
            return MetamodelMocks.this.etC;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idC;
        }

        public ListAttribute<OWLClassC, OWLClassA> referencedListAtt() {
            return MetamodelMocks.this.cReferencedList;
        }

        public ListAttribute<OWLClassC, OWLClassA> simpleListAtt() {
            return MetamodelMocks.this.cSimpleList;
        }
    }

    public class OWLClassDMetamodel {
        public EntityTypeImpl<OWLClassD> entityType() {
            return MetamodelMocks.this.etD;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idD;
        }

        public SingularAttribute<OWLClassD, OWLClassA> owlClassAAtt() {
            return MetamodelMocks.this.dOwlClassAAtt;
        }
    }

    public class OWLClassEMetamodel {
        public EntityTypeImpl<OWLClassE> entityType() {
            return MetamodelMocks.this.etE;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idE;
        }

        public SingularAttribute<OWLClassE, String> stringAttribute() {
            return MetamodelMocks.this.eStringAtt;
        }
    }

    public class OWLClassFMetamodel {
        public EntityTypeImpl<OWLClassF> entityType() {
            return MetamodelMocks.this.etF;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idF;
        }

        public SingularAttribute<OWLClassF, String> stringAttribute() {
            return MetamodelMocks.this.fStringAtt;
        }

        public PluralAttribute<OWLClassF, Set, OWLClassA> setAttribute() {
            return MetamodelMocks.this.fSetAtt;
        }
    }

    public class OWLClassGMetamodel {
        public EntityTypeImpl<OWLClassG> entityType() {
            return MetamodelMocks.this.etG;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idG;
        }

        public SingularAttribute<OWLClassG, OWLClassH> owlClassHAtt() {
            return MetamodelMocks.this.gOwlClassHAtt;
        }
    }

    public class OWLClassHMetamodel {
        public EntityTypeImpl<OWLClassH> entityType() {
            return MetamodelMocks.this.etH;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idH;
        }

        public SingularAttribute<OWLClassH, OWLClassG> owlClassGAtt() {
            return MetamodelMocks.this.hOwlClassGAtt;
        }

        public SingularAttribute<OWLClassH, OWLClassA> owlClassAAtt() {
            return MetamodelMocks.this.hOwlClassAAtt;
        }

    }

    public class OWLClassJMetamodel {
        public EntityTypeImpl<OWLClassJ> entityType() {
            return MetamodelMocks.this.etJ;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idJ;
        }

        public PluralAttribute<OWLClassJ, Set, OWLClassA> setAttribute() {
            return MetamodelMocks.this.jSetAtt;
        }
    }

    public class OWLClassKMetamodel {
        public EntityTypeImpl<OWLClassK> entityType() {
            return MetamodelMocks.this.etK;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idK;
        }

        public SingularAttribute<OWLClassK, OWLClassE> owlClassEAtt() {
            return MetamodelMocks.this.kOwlClassEAtt;
        }
    }

    public class OWLClassLMetamodel {
        public EntityTypeImpl<OWLClassL> entityType() {
            return MetamodelMocks.this.etL;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idL;
        }

        public ListAttribute<OWLClassL, OWLClassA> referencedListAtt() {
            return MetamodelMocks.this.lReferencedList;
        }

        public ListAttribute<OWLClassL, OWLClassA> simpleListAtt() {
            return MetamodelMocks.this.lSimpleList;
        }

        public PluralAttribute<OWLClassL, Set, OWLClassA> setAttribute() {
            return MetamodelMocks.this.lSetAtt;
        }

        public SingularAttribute<OWLClassL, OWLClassA> owlClassAAtt() {
            return MetamodelMocks.this.lOwlClassAAtt;
        }
    }

    public class OWLClassMMetamodel {
        public EntityTypeImpl<OWLClassM> entityType() {
            return MetamodelMocks.this.etM;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idM;
        }

        public SingularAttribute<OWLClassM, Boolean> booleanAttribute() {
            return MetamodelMocks.this.mBooleanAtt;
        }

        public SingularAttribute<OWLClassM, Integer> integerAttribute() {
            return MetamodelMocks.this.mIntegerAtt;
        }

        public SingularAttribute<OWLClassM, Long> longAttribute() {
            return MetamodelMocks.this.mLongAtt;
        }

        public SingularAttribute<OWLClassM, Double> doubleAttribute() {
            return MetamodelMocks.this.mDoubleAtt;
        }

        public SingularAttribute<OWLClassM, Date> dateAttribute() {
            return MetamodelMocks.this.mDateAtt;
        }

        public SingularAttribute<OWLClassM, OWLClassM.Severity> enumAttribute() {
            return MetamodelMocks.this.mEnumAtt;
        }

        public PluralAttribute<OWLClassM, Set, Integer> integerSetAttribute() {
            return MetamodelMocks.this.mIntegerSetAtt;
        }
    }

    public class OWLClassNMetamodel {
        public EntityTypeImpl<OWLClassN> entityType() {
            return MetamodelMocks.this.etN;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idN;
        }

        public SingularAttribute<OWLClassN, String> annotationAttribute() {
            return MetamodelMocks.this.nAnnotationAtt;
        }

        public SingularAttribute<OWLClassN, URI> annotationUriAttribute() {
            return MetamodelMocks.this.nAnnotationUriAtt;
        }

        public SingularAttribute<OWLClassN, String> stringAttribute() {
            return MetamodelMocks.this.nStringAtt;
        }

        public PropertiesSpecification<OWLClassN, Map, String, String> properties() {
            return MetamodelMocks.this.nProperties;
        }
    }

    public class OWLClassOMetamodel {
        public EntityTypeImpl<OWLClassO> entityType() {
            return MetamodelMocks.this.etO;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idO;
        }

        public SingularAttribute<OWLClassO, String> stringAttribute() {
            return MetamodelMocks.this.oStringAtt;
        }
    }

    public class OWLClassPMetamodel {
        public EntityTypeImpl<OWLClassP> entityType() {
            return MetamodelMocks.this.etP;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idP;
        }

        public SingularAttribute<OWLClassP, URI> pUriAttribute() {
            return MetamodelMocks.this.pUriAtt;
        }

        public PluralAttribute<OWLClassP, Set, URL> pUrlsAttribute() {
            return MetamodelMocks.this.pUrlsAtt;
        }

        public ListAttribute<OWLClassP, URI> pSimpleListAttribute() {
            return MetamodelMocks.this.pSimpleList;
        }

        public ListAttribute<OWLClassP, URI> pReferencedListAttribute() {
            return MetamodelMocks.this.pReferencedList;
        }

        public TypesSpecification<OWLClassP, URI> types() {
            return MetamodelMocks.this.pTypes;
        }

        public PropertiesSpecification<OWLClassP, Map, URI, Object> properties() {
            return MetamodelMocks.this.pProperties;
        }
    }

    public class OWLClassQMetamodel {
        public EntityTypeImpl<OWLClassQ> entityType() {
            return MetamodelMocks.this.etQ;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idQ;
        }

        public SingularAttribute<OWLClassQ, String> qStringAtt() {
            return MetamodelMocks.this.qStringAtt;
        }

        public SingularAttribute<OWLClassQ, String> qParentStringAtt() {
            return MetamodelMocks.this.qParentStringAtt;
        }

        public SingularAttribute<OWLClassQ, String> qLabelAtt() {
            return MetamodelMocks.this.qLabelAtt;
        }

        public SingularAttribute<OWLClassQ, OWLClassA> qOwlClassAAtt() {
            return MetamodelMocks.this.qOwlClassAAtt;
        }
    }

    public class OWLClassRMetamodel {
        public EntityTypeImpl<OWLClassR> entityType() {
            return MetamodelMocks.this.etR;
        }

        public SingularAttribute<OWLClassR, String> rStringAtt() {
            return MetamodelMocks.this.rStringAtt;
        }

        public SingularAttribute<OWLClassR, OWLClassA> rOwlClassAAtt() {
            return MetamodelMocks.this.rOwlClassAAtt;
        }

        public ConcreteListener concreteListener() {
            return MetamodelMocks.this.concreteListenerMock;
        }

        public AnotherListener anotherListener() {
            return MetamodelMocks.this.anotherListenerMock;
        }
    }

    public class OWLClassSMetamodel {

        public EntityTypeImpl<OWLClassS> entityType() {
            return MetamodelMocks.this.etS;
        }

        public Identifier identifier() {
            return MetamodelMocks.this.idS;
        }

        public SingularAttribute<OWLClassS, String> rNameAtt() {
            return MetamodelMocks.this.sNameAtt;
        }

        public TypesSpecification<OWLClassS, String> types() {
            return MetamodelMocks.this.sTypes;
        }

        public ParentListener parentListener() {
            return MetamodelMocks.this.parentListenerMock;
        }
    }
}
