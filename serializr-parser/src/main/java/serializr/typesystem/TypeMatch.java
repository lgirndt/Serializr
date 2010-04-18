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
package serializr.typesystem;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/*
*
*/
public class TypeMatch {

    private String typeName;
    private SerializrPackage pkg;

    static public TypeMatch createFullTypeMatch(String typeName, SerializrPackage pkg) {
        return createFullTypeMatch(typeName, pkg.getPackageNames());
    }

    static public TypeMatch createFullTypeMatch(String typeName, List<String> packageName) {
        return new TypeMatch(typeName, packageName);
    }

    static public TypeMatch createFullTypeMatch(String typeName, String... packageName) {
        return new TypeMatch(typeName, Arrays.asList(packageName));
    }

    static public TypeMatch createLocalTypeMatch(String typeName) {
        return new TypeMatch(typeName);
    }

    static public TypeMatch createPrimitiveTypeMatch(String typeName) {
        return new TypeMatch(typeName, Builtins.BUILTIN_PKG);
    }

    private TypeMatch(String typeName) {
        this.typeName = typeName;
        this.pkg = null;
    }

    private TypeMatch(String typeName, List<String> packageName) {
        this(typeName, new SerializrPackage(packageName));
    }

    private TypeMatch(String typeName, SerializrPackage pck) {
        this.typeName = typeName;
        this.pkg = pck;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<String> getPackageNames() {
        return pkg.getPackageNames();
    }

    public SerializrPackage getPackage() {
        return pkg;
    }

    public boolean matches(TypeMatch match) {
        if (isInconcrete()) {
            return typeName.equals(match.typeName);
        }
        return this.equals(match);
    }

    private boolean isInconcrete() {
        return pkg == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeMatch typeMatch = (TypeMatch) o;

        if (pkg != null ? !pkg.equals(typeMatch.pkg) : typeMatch.pkg != null)
            return false;
        if (typeName != null ? !typeName.equals(typeMatch.typeName) : typeMatch.typeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeName != null ? typeName.hashCode() : 0;
        result = 31 * result + (pkg != null ? pkg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        List<String> result = Lists.newArrayList();
        if (!isInconcrete()) {
            result.addAll(getPackageNames());
        }
        result.add(getTypeName());
        return Joiner.on(".").join(result);
    }
}
