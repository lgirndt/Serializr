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
package serializr.ast;

import org.antlr.runtime.Token;
import serializr.typesystem.*;

/*
*
*/
class CollectionTypeRefNode extends Node implements TypeRef {

    private class CollectionTypeImpl implements CollectionType {

        @Override
        public Type getElementType() {
            return getElementTypeRef().getSerializrType();
        }

        @Override
        public String getName() {
            return "List[" + getElementType().getName() + "]";
        }

        @Override
        public SerializrPackage getPackage() {
            return Builtins.BUILTIN_PKG;
        }
    }

    private CollectionType collectionType = new CollectionTypeImpl();

    CollectionTypeRefNode(Token t) {
        super(t);
    }

    @Override
    public Type getSerializrType() {
        return collectionType;
    }

    @Override
    public void applySerializrType(Type type) {
        // nothing?
    }

    @Override
    public TypeMatch getTypeMatch() {
        // TODO does this work?
        return null;
    }

    private TypeRef getElementTypeRef() {
        return (TypeRef) getChild(0);
    }
}
