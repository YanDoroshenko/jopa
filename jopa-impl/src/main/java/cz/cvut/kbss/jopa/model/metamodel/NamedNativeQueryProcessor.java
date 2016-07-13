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
package cz.cvut.kbss.jopa.model.metamodel;

import cz.cvut.kbss.jopa.model.annotations.NamedNativeQueries;
import cz.cvut.kbss.jopa.model.annotations.NamedNativeQuery;
import cz.cvut.kbss.jopa.query.NamedQueryManager;

import java.util.List;

class NamedNativeQueryProcessor {

    private final NamedQueryManager queryManager;

    NamedNativeQueryProcessor(NamedQueryManager queryManager) {
        this.queryManager = queryManager;
    }

    /**
     * Discovers named native queries in the specified class.
     * <p>
     * The queries (if found) are added to the {@code NamedQueryManager}, which was passed to this class in constructor.
     *
     * @param cls The class to process
     */
    <T> void processClass(Class<T> cls) {
        final List<Class<? super T>> hierarchy = EntityClassProcessor.getEntityHierarchy(cls);
        for (Class<? super T> c : hierarchy) {
            final NamedNativeQueries queries = c.getAnnotation(NamedNativeQueries.class);
            if (queries != null) {
                for (NamedNativeQuery q : queries.value()) {
                    processQuery(q);
                }
            }
            final NamedNativeQuery nq = c.getAnnotation(NamedNativeQuery.class);
            if (nq != null) {
                processQuery(nq);
            }
        }
    }

    private void processQuery(NamedNativeQuery query) {
        queryManager.addNamedQuery(query.name(), query.query());
    }
}
