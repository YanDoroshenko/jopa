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
package cz.cvut.kbss.jopa.model.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OWLAnnotationProperty {
    /**
     * IRI of the annotation property
     *
     * @return IRI of the annotation property
     */
    String iri();

    /**
     * Denotes a member that is inferred (true) using the OWL reasoner or just
     * asserted (false).
     *
     * @return Whether this property is read only
     */
    boolean readOnly() default false;

    /**
     * (Optional) Whether the association should be lazily loaded or must be eagerly fetched.
     *
     * @return Fetch type of this property
     */
    FetchType fetch() default FetchType.EAGER;
}
