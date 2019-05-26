package org.jetrbains.dukat.nodeLowering

import org.jetbrains.dukat.ast.model.duplicate
import org.jetbrains.dukat.ast.model.nodes.ClassLikeNode
import org.jetbrains.dukat.ast.model.nodes.ClassNode
import org.jetbrains.dukat.ast.model.nodes.DocumentRootNode
import org.jetbrains.dukat.ast.model.nodes.FunctionNode
import org.jetbrains.dukat.ast.model.nodes.FunctionTypeNode
import org.jetbrains.dukat.ast.model.nodes.InterfaceNode
import org.jetbrains.dukat.ast.model.nodes.MemberNode
import org.jetbrains.dukat.ast.model.nodes.ObjectNode
import org.jetbrains.dukat.ast.model.nodes.ParameterNode
import org.jetbrains.dukat.ast.model.nodes.TypeAliasNode
import org.jetbrains.dukat.ast.model.nodes.UnionTypeNode
import org.jetbrains.dukat.ast.model.nodes.TypeValueNode
import org.jetbrains.dukat.ast.model.nodes.VariableNode
import org.jetbrains.dukat.astCommon.TopLevelEntity
import org.jetbrains.dukat.astCommon.TypeEntity
import org.jetbrains.dukat.ownerContext.NodeOwner
import org.jetbrains.dukat.tsmodel.types.IntersectionTypeDeclaration
import org.jetbrains.dukat.tsmodel.types.TupleDeclaration
import org.jetbrains.dukat.tsmodel.types.UnionTypeDeclaration

interface NodeWithOwnerLowering<T:TypeEntity> {
    fun lowerVariableNode(owner: NodeOwner<VariableNode>): VariableNode
    fun lowerFunctionNode(owner: NodeOwner<FunctionNode>): FunctionNode
    fun lowerClassNode(owner: NodeOwner<ClassNode>): ClassNode
    fun lowerInterfaceNode(owner: NodeOwner<InterfaceNode>): InterfaceNode

    fun lowerParameterNode(owner: NodeOwner<ParameterNode>): ParameterNode
    fun lowerTypeParameter(owner: NodeOwner<TypeValueNode>): TypeValueNode
    fun lowerMemberNode(owner: NodeOwner<MemberNode>): MemberNode
    fun lowerTypeAliasNode(owner: NodeOwner<TypeAliasNode>): TypeAliasNode
    fun lowerObjectNode(owner: NodeOwner<ObjectNode>): ObjectNode

    fun lowerTypeNode(owner: NodeOwner<TypeValueNode>): T
    fun lowerFunctionTypeNode(owner: NodeOwner<FunctionTypeNode>): T
    fun lowerUnionTypeDeclaration(owner: NodeOwner<UnionTypeDeclaration>): T
    fun lowerUnionTypeNode(owner: NodeOwner<UnionTypeNode>): T
    fun lowerIntersectionTypeDeclaration(owner: NodeOwner<IntersectionTypeDeclaration>): T
    fun lowerTupleDeclaration(owner: NodeOwner<TupleDeclaration>): T

    fun lowerClassLikeNode(owner: NodeOwner<ClassLikeNode>): ClassLikeNode {
        val declaration = owner.node
        return when (declaration) {
            is InterfaceNode -> lowerInterfaceNode(owner.wrap(declaration))
            is ClassNode -> lowerClassNode(owner.wrap(declaration))
            is ObjectNode -> lowerObjectNode(owner.wrap(declaration))
            else -> declaration
        }
    }

    fun lowerTopLevelEntity(owner: NodeOwner<TopLevelEntity>): TopLevelEntity {
        val declaration = owner.node
        return when (declaration) {
            is VariableNode -> lowerVariableNode(owner.wrap(declaration))
            is FunctionNode -> lowerFunctionNode(owner.wrap(declaration))
            is ClassLikeNode -> lowerClassLikeNode(owner.wrap(declaration))
            is DocumentRootNode -> lowerRoot(declaration, owner.wrap(declaration))
            is TypeAliasNode -> lowerTypeAliasNode(owner.wrap(declaration))
            else -> declaration.duplicate()
        }
    }

    fun lowerTopLevelDeclarations(declarations: List<TopLevelEntity>, owner: NodeOwner<DocumentRootNode>): List<TopLevelEntity> {
        return declarations.map { declaration ->
            lowerTopLevelEntity(owner.wrap(declaration))
        }
    }

    fun lowerRoot(documentRoot: DocumentRootNode, owner: NodeOwner<DocumentRootNode>): DocumentRootNode {
        return documentRoot.copy(
                declarations = lowerTopLevelDeclarations(documentRoot.declarations, owner)
        )
    }
}