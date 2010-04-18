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
package serializr.typesystem.simples;

import serializr.typesystem.SerializrPackage;
import serializr.typesystem.Type;
import serializr.typesystem.TypeMatch;
import serializr.typesystem.TypeRef;

/*
*
*/
public class SimpleTypeRef implements TypeRef {

    private final String typeName;
    private final SerializrPackage pkg;

    public SimpleTypeRef(String typeName) {
        this.typeName = typeName;
        this.pkg = null;
    }

    public SimpleTypeRef(String typeName, String... packageNames) {
        this(typeName, new SerializrPackage(packageNames));
    }

    public SimpleTypeRef(String typeName, SerializrPackage pkg) {
        this.typeName = typeName;
        this.pkg = pkg;
    }

    @Override
    public Type getSerializrType() {
        // TODO
        return null;
    }

    @Override
    public TypeMatch getTypeMatch() {
        if (pkg == null) {
            return TypeMatch.createLocalTypeMatch(typeName);
        } else {
            return TypeMatch.createFullTypeMatch(typeName, pkg);
        }
    }
}
