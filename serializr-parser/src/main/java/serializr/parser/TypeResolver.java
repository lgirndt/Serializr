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

import serializr.ast.TypeParsingEventListener;
import serializr.typesystem.Builtins;
import serializr.typesystem.TranslationUnit;
import serializr.typesystem.Type;
import serializr.typesystem.TypeRef;

/*
*
*/
class TypeResolver implements TypeParsingEventListener {

    private final ErrorReporter errorReporter;
    private final TypeLookup typeLookup;

    public TypeResolver(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
        this.typeLookup = setupLookup(errorReporter);
    }

    private TypeLookup setupLookup(ErrorReporter errorReporter) {
        TypeLookup typeLookup = new TypeLookup(errorReporter);
        addBuiltinTypes(typeLookup);
        return typeLookup;
    }

    private void addBuiltinTypes(TypeLookup typeLookup) {
        for (Type builtin : Builtins.TYPES) {
            typeLookup.foundType(builtin);
        }
    }

    public void resolve(TranslationUnit unit) {
        typeLookup.fillTypeMap();
        for (TypeRef ref : typeLookup.getTypeRefs()) {
            Type foundType = typeLookup.lookup(ref, unit.getPackage());
            if (foundType == null) {
                errorReporter.reportError("Cannot resolve type reference '" + ref + "'");
                continue;
            }
            ref.applySerializrType(foundType);
        }
    }

    @Override
    public void foundType(Type type) {
        typeLookup.foundType(type);
    }

    @Override
    public void foundTypeRef(TypeRef typeRef) {
        typeLookup.foundTypeRef(typeRef);
    }
}
