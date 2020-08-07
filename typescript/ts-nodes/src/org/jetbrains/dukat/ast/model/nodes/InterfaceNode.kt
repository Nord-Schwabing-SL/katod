package org.jetbrains.dukat.ast.model.nodes

import org.jetbrains.dukat.astCommon.NameEntity
import org.jetbrains.dukat.tsmodel.HeritageClauseDeclaration
import org.jetbrains.dukat.tsmodel.MemberDeclaration
import org.jetbrains.dukat.tsmodel.types.TypeDeclaration

data class InterfaceNode(
        override val name: NameEntity,
        override val members: List<MemberDeclaration>,
        val typeParameters: List<TypeDeclaration>,
        val parentEntities: List<HeritageClauseDeclaration>,

        val generated: Boolean,
        override val uid: String,
        val external: Boolean
) : ClassLikeNode