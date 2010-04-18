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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Set;

/*
*
*/
public class Builtins {

    public static final String BUILTIN_PKG_NAME = "__builtin__";
    public static final SerializrPackage BUILTIN_PKG = new SerializrPackage(BUILTIN_PKG_NAME);

    public static final PrimitiveType BUILTIN_CHAR = new BuiltinPrimitiveType("Char");
    public static final PrimitiveType BUILTIN_BYTE = new BuiltinPrimitiveType("Byte");
    public static final PrimitiveType BUILTIN_SHORT = new BuiltinPrimitiveType("Short");
    public static final PrimitiveType BUILTIN_INT = new BuiltinPrimitiveType("Int");
    public static final PrimitiveType BUILTIN_LONG = new BuiltinPrimitiveType("Long");
    public static final PrimitiveType BUILTIN_DOUBLE = new BuiltinPrimitiveType("Double");
    public static final PrimitiveType BUILTIN_FLOAT = new BuiltinPrimitiveType("Float");
    public static final PrimitiveType BUILTIN_STRING = new BuiltinPrimitiveType("String");
    public static final PrimitiveType BUILTIN_BOOLEAN = new BuiltinPrimitiveType("Boolean");

    public static final Set<PrimitiveType> TYPES = ImmutableSet.<PrimitiveType>builder().addAll(
            Sets.newHashSet(
                    BUILTIN_CHAR,
                    BUILTIN_BYTE,
                    BUILTIN_SHORT,
                    BUILTIN_INT,
                    BUILTIN_LONG,
                    BUILTIN_DOUBLE,
                    BUILTIN_FLOAT,
                    BUILTIN_STRING,
                    BUILTIN_BOOLEAN)).build();

    private static final class BuiltinPrimitiveType implements PrimitiveType {

        private final String name;

        private BuiltinPrimitiveType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public SerializrPackage getPackage() {
            return BUILTIN_PKG;
        }
    }
}
