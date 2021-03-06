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
package cz.cvut.kbss.ontodriver.config;

import cz.cvut.kbss.ontodriver.OntologyStorageProperties;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private Configuration configuration = new Configuration(
            OntologyStorageProperties.driver("cz.cvut.kbss.ontodriver.sesame.SesameDataSource")
                                     .physicalUri("memory-store").build());

    @Test
    public void addConfigurationFiltersKnownConfigurationProperties() {
        final Map<String, String> props = new HashMap<>();
        props.put(OntoDriverProperties.CONNECTION_AUTO_COMMIT, "true");
        props.put(OntoDriverProperties.ONTOLOGY_LANGUAGE, "en");
        props.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, "false");

        configuration.addConfiguration(props,
                Arrays.asList(ConfigParam.AUTO_COMMIT, ConfigParam.MODULE_EXTRACTION_SIGNATURE,
                        ConfigParam.USE_TRANSACTIONAL_ONTOLOGY));
        assertNotNull(configuration.getProperty(ConfigParam.AUTO_COMMIT));
        assertNotNull(configuration.getProperty(ConfigParam.USE_TRANSACTIONAL_ONTOLOGY));
        assertNull(configuration.getProperty(ConfigParam.ONTOLOGY_LANGUAGE));   // Not in the known properties
    }

    @Test
    public void getWithDefaultReturnsDefaultWhenConfigParamValueIsNotFound() {
        assertNull(configuration.getProperty(ConfigParam.ONTOLOGY_LANGUAGE));
        final String defaultLang = "en";
        assertEquals(defaultLang, configuration.getProperty(ConfigParam.ONTOLOGY_LANGUAGE, defaultLang));
    }

    @Test
    public void testGetPropertyAsBoolean() {
        configuration.setProperty(ConfigParam.AUTO_COMMIT, "true");
        final boolean res = configuration.is(ConfigParam.AUTO_COMMIT);
        assertTrue(res);
    }

    @Test
    public void getAsBooleanReturnsFalseForUnknownProperty() {
        final boolean res = configuration.is(ConfigParam.AUTO_COMMIT);
        assertFalse(res);
    }
}