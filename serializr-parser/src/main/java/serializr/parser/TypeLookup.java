/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package serializr.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import serializr.ast.TypeParsingEventListener;
import serializr.typesystem.SerializrPackage;
import serializr.typesystem.Type;
import serializr.typesystem.TypeMatch;
import serializr.typesystem.TypeRef;

import java.util.List;
import java.util.Map;

/*
* Tracks typeMap and typerefs.
*/
class TypeLookup implements TypeParsingEventListener {

    private final ErrorReporter errorReporter;

    private final Map<TypeMatch, Type> typeMap = Maps.newHashMap();

    private List<TypeRef> typeRefs = Lists.newArrayList();

    public TypeLookup(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @Override
    public void typeFound(Type type) {
        TypeMatch key = TypeMatch.toTypeMatch(type);

        if (typeMap.containsKey(key)) {
            errorReporter.reportError("Type " + type.getName() + " already exists.");
        } else {
            typeMap.put(key, type);
        }
    }

    @Override
    public void typeRefFound(TypeRef typeRef) {
        typeRefs.add(typeRef);
    }

    public Iterable<Type> getTypes() {
        return typeMap.values();
    }

    public Iterable<TypeRef> getTypeRefs() {
        return typeRefs;
    }

    public Type lookup(TypeRef typeRef, SerializrPackage currentPackage) {
        TypeMatch match = typeRef.getTypeMatch();
        if (match.isLocal()) {
            TypeMatch qualifiedMatch = deriveQualifiedMatch(currentPackage, match);
            return typeMap.get(qualifiedMatch);
        } else {
            return typeMap.get(match);
        }
    }

    private TypeMatch deriveQualifiedMatch(SerializrPackage currentPackage, TypeMatch match) {
        return TypeMatch.createFullTypeMatch(
                match.getTypeName(),
                currentPackage
        );
    }

}
