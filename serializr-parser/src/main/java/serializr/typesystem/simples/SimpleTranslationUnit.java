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

import serializr.typesystem.Role;
import serializr.typesystem.Sequence;
import serializr.typesystem.SerializrPackage;
import serializr.typesystem.TranslationUnit;

import java.util.List;

/*
*
*/
public class SimpleTranslationUnit implements TranslationUnit {

    private final SerializrPackage pkg;
    private final List<Role> roles;
    private final List<Sequence> sequences;

    public SimpleTranslationUnit(SerializrPackage pkg, List<Role> roles, List<Sequence> sequences) {
        this.pkg = pkg;
        this.roles = roles;
        this.sequences = sequences;
    }

    @Override
    public SerializrPackage getPackage() {
        return pkg;
    }

    @Override
    public Iterable<? extends Role> getRoles() {
        return roles;
    }

    @Override
    public Iterable<? extends Sequence> getSequences() {
        return sequences;
    }
}
