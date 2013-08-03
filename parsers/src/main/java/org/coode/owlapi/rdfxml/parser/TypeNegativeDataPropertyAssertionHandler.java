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
package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br>
 * <br> */

public class TypeNegativeDataPropertyAssertionHandler extends BuiltInTypeHandler {
    public TypeNegativeDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer, DeprecatedVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION);
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object)
            throws UnloadableImportException {
        IRI source = getConsumer().getResourceObject(subject,
                OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL.getIRI(), true);
        if (source == null) {
            source = getConsumer().getResourceObject(subject,
                    DeprecatedVocabulary.OWL_SUBJECT, true);
        }
        if (source == null) {
            source = getConsumer().getResourceObject(subject,
                    DeprecatedVocabulary.RDF_SUBJECT, true);
        }
        IRI property = getConsumer().getResourceObject(subject,
                OWLRDFVocabulary.OWL_ASSERTION_PROPERTY.getIRI(), true);
        if (property == null) {
            property = getConsumer().getResourceObject(subject,
                    DeprecatedVocabulary.OWL_PREDICATE, true);
        }
        if (property == null) {
            property = getConsumer().getResourceObject(subject,
                    DeprecatedVocabulary.RDF_PREDICATE, true);
        }
        OWLLiteral target = getConsumer().getLiteralObject(subject,
                OWLRDFVocabulary.OWL_TARGET_VALUE.getIRI(), true);
        if (target == null) {
            target = getConsumer().getLiteralObject(subject,
                    DeprecatedVocabulary.OWL_OBJECT, true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject,
                    DeprecatedVocabulary.RDF_OBJECT, true);
        }
        OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
        OWLDataPropertyExpression prop = getConsumer().translateDataPropertyExpression(
                property);
        consumeTriple(subject, predicate, object);
        getConsumer().translateAnnotations(subject);
        Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
        addAxiom(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(prop,
                sourceInd, target, annos));
    }
}