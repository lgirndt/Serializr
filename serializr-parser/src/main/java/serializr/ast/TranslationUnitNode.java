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
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import serializr.typesystem.Role;
import serializr.typesystem.Sequence;
import serializr.typesystem.SerializrPackage;
import serializr.typesystem.TranslationUnit;

import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;

/*
*
*/
public class TranslationUnitNode extends Node implements TranslationUnit {

    private SerializrPackage serializrPackage;

    public TranslationUnitNode(Token t) {
        super(t);
    }

    @Override
    public SerializrPackage getPackage() {

        if (serializrPackage == null) {
            serializrPackage = createPackage();
        }
        return serializrPackage;
    }

    private SerializrPackage createPackage() {
        Tree qname = getChild(0).getChild(0);
        List<String> pkgs = Lists.newArrayList();
        for (Tree child : new TreeChildrenIterable(qname)) {
            pkgs.add(child.getText());
        }
        return new SerializrPackage(pkgs);
    }

    private Tree getTypes() {
        return getChild(1);
    }

    @Override
    public Iterable<? extends Role> getRoles() {
        return transform(
                filter(new TreeChildrenIterable(getTypes()),
                        getRolePredicate()),
                getRoleCast()
        );
    }

    @Override
    public Iterable<? extends Sequence> getSequences() {
        return transform(filter(new TreeChildrenIterable(getTypes()),
                getSequencePredicate()),
                getSequenceCast());
    }

    private Function<Tree, Sequence> getSequenceCast() {
        return new Function<Tree, Sequence>() {

            @Override
            public Sequence apply(Tree tree) {
                return (Sequence) tree;
            }
        };
    }

    private Predicate<Tree> getSequencePredicate() {
        return new Predicate<Tree>() {

            @Override
            public boolean apply(Tree tree) {
                return tree instanceof Sequence;
            }
        };
    }

    private Function<Tree, Role> getRoleCast() {
        return new Function<Tree, Role>() {

            @Override
            public Role apply(Tree tree) {
                return (Role) tree;
            }
        };
    }

    private Predicate<Tree> getRolePredicate() {
        return new Predicate<Tree>() {

            @Override
            public boolean apply(Tree tree) {
                return tree instanceof Role;
            }
        };
    }
}
