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

import org.junit.Before;
import org.junit.Test;
import serializr.typesystem.SerializrPackage;
import serializr.typesystem.TranslationUnit;
import serializr.typesystem.Type;
import serializr.typesystem.TypeRef;
import serializr.typesystem.simples.SimpleType;
import serializr.typesystem.simples.SimpleTypeRef;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
*
*/
public class TypeResolverTest {
    private TestReporter reporter;
    private TypeResolver resolver;
    private SerializrPackage pkg;

    private TranslationUnit unit;

    @Before
    public void setUp() throws Exception {
        reporter = new TestReporter();
        resolver = new TypeResolver(reporter);
        pkg = new SerializrPackage("bar");
        unit = createMock(TranslationUnit.class);
        expect(unit.getPackage()).andReturn(pkg);
    }

    @Test
    public void testResolveFullType() throws Exception {

        Type type = new SimpleType("Foo", pkg);
        resolver.foundType(type);

        TypeRef typeRef = new SimpleTypeRef("Foo", pkg);
        resolver.foundTypeRef(typeRef);

        replay(unit);

        resolver.resolve(unit);
        assertSuccessfulResolution(type, typeRef);
    }

    @Test
    public void testResolveLocalType() throws Exception {
        Type type = new SimpleType("Foo", pkg);
        resolver.foundType(type);

        TypeRef typeRef = new SimpleTypeRef("Foo");
        resolver.foundTypeRef(typeRef);

        replay(unit);

        resolver.resolve(unit);
        assertSuccessfulResolution(type, typeRef);
    }

    @Test
    public void testResolveOnNonExistingType() throws Exception {
        TypeRef typeRef = new SimpleTypeRef("Foo");
        resolver.foundTypeRef(typeRef);
        replay(unit);
        resolver.resolve(unit);
        reporter.assertError(1);
    }

    private void assertSuccessfulResolution(Type type, TypeRef typeRef) {
        reporter.assertNoError();
        verify(unit);
        assertThat(typeRef.getSerializrType(), is(type));
    }
}
