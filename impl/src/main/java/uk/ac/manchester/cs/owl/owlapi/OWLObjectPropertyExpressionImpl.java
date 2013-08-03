/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.util.ObjectPropertySimplifier;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br>
 * <br> */
public abstract class OWLObjectPropertyExpressionImpl extends OWLPropertyExpressionImpl
        implements OWLObjectPropertyExpression {
    private static final long serialVersionUID = 40000L;
    private OWLObjectPropertyExpression simplestForm;
    private OWLObjectPropertyExpression inverse;

    @Override
    public boolean isObjectPropertyExpression() {
        return true;
    }

    @Override
    public boolean isDataPropertyExpression() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLObjectPropertyExpression;
    }

    @Override
    public OWLObjectPropertyExpression getSimplified() {
        if (simplestForm == null) {
            ObjectPropertySimplifier simplifier = new ObjectPropertySimplifier(df);
            simplestForm = simplifier.getSimplified(this);
        }
        return simplestForm;
    }

    @Override
    public OWLObjectPropertyExpression getInverseProperty() {
        if (inverse == null) {
            inverse = df.getOWLObjectInverseOf(this);
        }
        return inverse;
    }

    @Override
    public OWLObjectProperty getNamedProperty() {
        OWLObjectPropertyExpression simp = getSimplified();
        if (simp.isAnonymous()) {
            return ((OWLObjectInverseOf) simp).getInverse().asOWLObjectProperty();
        } else {
            return simp.asOWLObjectProperty();
        }
    }
}