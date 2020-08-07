package org.jetbrains.dukat.ast.model.nodes

import org.jetbrains.dukat.astCommon.IdentifierEntity
import org.jetbrains.dukat.astCommon.MemberEntity
import org.jetbrains.dukat.astCommon.NameEntity
import org.jetbrains.dukat.tsmodel.BlockDeclaration
import org.jetbrains.dukat.tsmodel.ParameterDeclaration
import org.jetbrains.dukat.tsmodel.TopLevelDeclaration
import org.jetbrains.dukat.tsmodel.types.ParameterValueDeclaration
import org.jetbrains.dukat.tsmodel.types.TypeDeclaration

sealed class FunctionNodeContext
data class FunctionFromMethodSignatureDeclaration(val name: String, val params: List<IdentifierEntity>) : FunctionNodeContext()
data class IndexSignatureGetter(val name: String) : FunctionNodeContext()
data class IndexSignatureSetter(val name: String) : FunctionNodeContext()
data class FunctionFromCallSignature(val params: List<IdentifierEntity>) : FunctionNodeContext()
class FunctionNodeContextIrrelevant : FunctionNodeContext()

data class FunctionNode(
        val name: NameEntity,
        override val parameters: List<ParameterDeclaration>,
        val type: ParameterValueDeclaration,
        val typeParameters: List<TypeDeclaration>,

        val export: Boolean,
        val inline: Boolean,
        val operator: Boolean,

        val extend: ClassLikeReferenceNode?,
        val context: FunctionNodeContext,
        val uid: String,

        val body: BlockDeclaration?,
        val external: Boolean,
        val isGenerator: Boolean
) : MemberEntity, TopLevelDeclaration, ParameterOwnerNode