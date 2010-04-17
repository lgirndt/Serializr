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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import serializr.typesystem.Field;
import serializr.typesystem.RoleRef;
import serializr.typesystem.Sequence;
import serializr.typesystem.SerializrPackage;

/*
*
*/
public class SequenceNode extends DefaultNode implements Sequence {

    public SequenceNode(Token t) {
        super(t);
    }

    @Override
    public String getName() {
        return getChild(0).getText();
    }

    @Override
    public SerializrPackage getPackage() {
        throw new RuntimeException("IMPLEMENT ME");
    }

    @Override
    public Iterable<? extends Field> getFields() {
        Tree seqBody = getChild(1);
        return Iterables.transform(new TreeChildrenIterable(seqBody), toFieldCast());
    }

    @Override
    public Iterable<? extends RoleRef> getRoleRefs() {
        Tree roleRefs = getChild(2);
        return Iterables.transform(new TreeChildrenIterable(roleRefs), toRoleRefCast());
    }

    private Function<Tree, Field> toFieldCast() {
        return new Function<Tree, Field>() {

            @Override
            public FieldNode apply(Tree tree) {
                return (FieldNode) tree;
            }
        };
    }

    private Function<Tree, RoleRef> toRoleRefCast() {
        return new Function<Tree, RoleRef>() {

            @Override
            public RoleRef apply(Tree tree) {
                return (RoleRef) tree;
            }
        };
    }
}
