package org.jetbrains.dukat.translatorString

import org.jetbrains.dukat.astCommon.CommentEntity
import org.jetbrains.dukat.astCommon.IdentifierEntity
import org.jetbrains.dukat.astCommon.SimpleCommentEntity
import org.jetbrains.dukat.astModel.AnnotationModel
import org.jetbrains.dukat.astModel.ClassLikeReferenceModel
import org.jetbrains.dukat.astModel.ClassModel
import org.jetbrains.dukat.astModel.ConstructorModel
import org.jetbrains.dukat.astModel.DelegationModel
import org.jetbrains.dukat.astModel.EnumModel
import org.jetbrains.dukat.astModel.ExternalDelegationModel
import org.jetbrains.dukat.astModel.FunctionModel
import org.jetbrains.dukat.astModel.FunctionTypeModel
import org.jetbrains.dukat.astModel.HeritageModel
import org.jetbrains.dukat.astModel.ImportModel
import org.jetbrains.dukat.astModel.InitBlockModel
import org.jetbrains.dukat.astModel.InterfaceModel
import org.jetbrains.dukat.astModel.LambdaParameterModel
import org.jetbrains.dukat.astModel.MemberModel
import org.jetbrains.dukat.astModel.MethodModel
import org.jetbrains.dukat.astModel.ModuleModel
import org.jetbrains.dukat.astModel.ObjectModel
import org.jetbrains.dukat.astModel.ParameterModel
import org.jetbrains.dukat.astModel.ParameterModifierModel
import org.jetbrains.dukat.astModel.PropertyModel
import org.jetbrains.dukat.astModel.TypeAliasModel
import org.jetbrains.dukat.astModel.TypeModel
import org.jetbrains.dukat.astModel.TypeParameterModel
import org.jetbrains.dukat.astModel.TypeParameterReferenceModel
import org.jetbrains.dukat.astModel.TypeValueModel
import org.jetbrains.dukat.astModel.VariableModel
import org.jetbrains.dukat.astModel.Variance
import org.jetbrains.dukat.astModel.comments.DocumentationCommentModel
import org.jetbrains.dukat.astModel.expressions.AsExpressionModel
import org.jetbrains.dukat.astModel.expressions.BinaryExpressionModel
import org.jetbrains.dukat.astModel.expressions.CallExpressionModel
import org.jetbrains.dukat.astModel.expressions.ConditionalExpressionModel
import org.jetbrains.dukat.astModel.expressions.ExpressionModel
import org.jetbrains.dukat.astModel.expressions.IdentifierExpressionModel
import org.jetbrains.dukat.astModel.expressions.IndexExpressionModel
import org.jetbrains.dukat.astModel.expressions.IsExpressionModel
import org.jetbrains.dukat.astModel.expressions.LambdaExpressionModel
import org.jetbrains.dukat.astModel.expressions.NonNullExpressionModel
import org.jetbrains.dukat.astModel.expressions.ParenthesizedExpressionModel
import org.jetbrains.dukat.astModel.expressions.DefinedExternallyExpressionModel
import org.jetbrains.dukat.astModel.expressions.PropertyAccessExpressionModel
import org.jetbrains.dukat.astModel.expressions.SuperExpressionModel
import org.jetbrains.dukat.astModel.expressions.ThisExpressionModel
import org.jetbrains.dukat.astModel.expressions.UnaryExpressionModel
import org.jetbrains.dukat.astModel.expressions.literals.BooleanLiteralExpressionModel
import org.jetbrains.dukat.astModel.expressions.literals.LiteralExpressionModel
import org.jetbrains.dukat.astModel.expressions.literals.NullLiteralExpressionModel
import org.jetbrains.dukat.astModel.expressions.literals.NumericLiteralExpressionModel
import org.jetbrains.dukat.astModel.expressions.literals.StringLiteralExpressionModel
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.AND
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.BITWISE_AND
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.BITWISE_OR
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.BITWISE_XOR
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.DIV
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.DIV_ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.EQ
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.GE
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.GT
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.LE
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.LT
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MINUS
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MINUS_ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MOD
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MOD_ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MULT
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.MULT_ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.NOT_EQ
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.OR
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.PLUS
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.PLUS_ASSIGN
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.REF_EQ
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.REF_NOT_EQ
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.SHIFT_LEFT
import org.jetbrains.dukat.astModel.expressions.operators.BinaryOperatorModel.SHIFT_RIGHT
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel.DECREMENT
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel.INCREMENT
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel.NOT
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel.UNARY_MINUS
import org.jetbrains.dukat.astModel.expressions.operators.UnaryOperatorModel.UNARY_PLUS
import org.jetbrains.dukat.astModel.expressions.templates.ExpressionTemplateTokenModel
import org.jetbrains.dukat.astModel.expressions.templates.StringTemplateTokenModel
import org.jetbrains.dukat.astModel.expressions.templates.TemplateExpressionModel
import org.jetbrains.dukat.astModel.expressions.templates.TemplateTokenModel
import org.jetbrains.dukat.astModel.isGeneric
import org.jetbrains.dukat.astModel.modifiers.InheritanceModifierModel
import org.jetbrains.dukat.astModel.modifiers.VisibilityModifierModel
import org.jetbrains.dukat.astModel.statements.AssignmentStatementModel
import org.jetbrains.dukat.astModel.statements.BlockStatementModel
import org.jetbrains.dukat.astModel.statements.BreakStatementModel
import org.jetbrains.dukat.astModel.statements.ContinueStatementModel
import org.jetbrains.dukat.astModel.statements.ExpressionStatementModel
import org.jetbrains.dukat.astModel.statements.ForInStatementModel
import org.jetbrains.dukat.astModel.statements.IfStatementModel
import org.jetbrains.dukat.astModel.statements.ReturnStatementModel
import org.jetbrains.dukat.astModel.statements.RunBlockStatementModel
import org.jetbrains.dukat.astModel.statements.StatementModel
import org.jetbrains.dukat.astModel.statements.ThrowStatementModel
import org.jetbrains.dukat.astModel.statements.WhenStatementModel
import org.jetbrains.dukat.astModel.statements.WhileStatementModel
import org.jetbrains.dukat.panic.raiseConcern
import org.jetbrains.dukat.translator.ModelVisitor
import org.jetbrains.dukat.translator.ROOT_PACKAGENAME

private const val FORMAT_TAB = "    "

private const val KEYWORD_EXTERNAL = "external"
private const val KEYWORD_INLINE = "inline"
private const val KEYWORD_OPERATOR = "operator"

private fun String?.translateMeta(): String {
    return if (this != null) {
        " /* ${this} */"
    } else {
        ""
    }
}


private fun CommentEntity.translate(): String {
    return when (this) {
        is SimpleCommentEntity -> text.translateMeta().trim()
        is DocumentationCommentModel -> {
            """/**
 * $text
 */"""
        }
        else -> ""
    }
}

private fun CommentEntity.translate(output: (String) -> Unit) {
    translate().split("\n").forEach(output)
}


private fun TypeModel.translateMeta(): String {
    return when (this) {
        is TypeParameterReferenceModel -> metaDescription.translateMeta()
        is TypeValueModel -> metaDescription.translateMeta()
        is FunctionTypeModel -> metaDescription.translateMeta()
        else -> ""
    }
}

private fun StatementModel.translateMeta(): String {
    return metaDescription.translateMeta()
}

private fun LambdaParameterModel.translate(): String {
    val paramNameSerialized = if (name != null && explicitlyDeclaredType) {
        "$name: "
    } else {
        name ?: ""
    }
    val type = if (explicitlyDeclaredType) {
        type.translate() + type.translateMeta()
    } else {
        ""
    }

    return "$paramNameSerialized$type"
}

private fun translateLambdaParameters(parameters: List<LambdaParameterModel>): String {
    return parameters.map {
        it.translate()
    }.joinToString(", ")
}

fun TypeModel.translate(): String {
    return when (this) {
        is TypeParameterReferenceModel -> {
            val res = mutableListOf(name.translate(shortNameForKotlin = false))
            if (nullable) {
                res.add("?")
            }
            res.joinToString("")
        }
        is TypeValueModel -> {
            val res = mutableListOf(value.translate(shortNameForKotlin = false))
            if (isGeneric()) {
                res.add(translateTypeParameters(params))
            }
            if (nullable) {
                res.add("?")
            }
            res.joinToString("")
        }
        is FunctionTypeModel -> {
            val res = mutableListOf("(")

            res.add(translateLambdaParameters(parameters) + ")")
            res.add(" -> ${type.translate()}")
            var translated = res.joinToString("")
            if (nullable) {
                translated = "(${translated})?"
            }
            translated
        }
        else -> return "failed to translateType ${this}"
    }
}

private fun ParameterModel.translate(needsMeta: Boolean = true): String {
    var res = name + ": " + type.translate()
    if (vararg) {
        res = "vararg $res"
    }

    modifier?.let {
        val modifierTranslated = when (it) {
            ParameterModifierModel.NOINLINE -> "noinline"
            ParameterModifierModel.CROSSINLINE -> "crossinline"
        }

        res = "$modifierTranslated $res"
    }

    if (needsMeta) {
        res += type.translateMeta()
    }

    initializer?.let {
        res += " = ${it.translateAsOneLine()}"
        if (needsMeta) {
            res += it.translateMeta()
        }
    }

    return res
}

private fun translateTypeParameters(typeParameters: List<TypeParameterModel>): String {
    return if (typeParameters.isEmpty()) {
        ""
    } else {
        "<" + typeParameters.map { typeParameter ->
            val varianceDescription = when (typeParameter.variance) {
                Variance.INVARIANT -> ""
                Variance.COVARIANT -> "out "
                Variance.CONTRAVARIANT -> "in "
            }
            val constraintDescription = if (typeParameter.constraints.isEmpty()) {
                ""
            } else {
                " : ${typeParameter.constraints[0].translate()}"
            }
            varianceDescription + typeParameter.type.translate() +
                    typeParameter.type.translateMeta() + constraintDescription
        }.joinToString(", ") + ">"
    }
}

private fun translateTypeArguments(typeParameters: List<TypeModel>): String {
    return if (typeParameters.isEmpty()) {
        ""
    } else {
        "<" + typeParameters.map {
            it.translate() + it.translateMeta()
        }.joinToString(", ") + ">"
    }
}


private fun translateParameters(parameters: List<ParameterModel>, needsMeta: Boolean = true): String {
    return parameters
            .map { parameter -> parameter.translate(needsMeta) }
            .joinToString(", ")
}


private fun AnnotationModel.translate(): String {
    val target = when (target) {
        AnnotationTarget.FILE -> "file:"
        else -> ""
    }
    var res = "@${target}" + name.translate(true)
    if (params.isNotEmpty()) {
        res = res + "(" + params.joinToString(", ") { "\"${it.translate()}\"" } + ")"
    }

    return res
}

private fun translateAnnotations(annotations: List<AnnotationModel>): String {
    val annotationsResolved = annotations.map { it.translate() }

    val annotationTranslated =
            if (annotationsResolved.isEmpty()) "" else annotationsResolved.joinToString(LINE_SEPARATOR) + LINE_SEPARATOR

    return annotationTranslated
}

private fun LambdaExpressionModel.translateMultiLine(): List<String> {
    val arguments = if (parameters.isNotEmpty()) " ${translateLambdaParameters(parameters)} ->" else ""
    return listOf("{$arguments") + body.statements.flatMap { it.translate() }.map { FORMAT_TAB + it } + listOf("}")
}

private fun CallExpressionModel.translateMultiLine(): List<String> {
    val lines = mutableListOf<String>()
    var currentString = "${expression.translate()}${if (typeParameters.isEmpty()) "" else "<${typeParameters.joinToString(", ") { it.translate() }}>"}("
    arguments.forEachIndexed { index, argument ->
        if (index != 0) {
            currentString += ", "
        }
        val argumentLines = argument.translateMultiLine()
        if (argumentLines.size > 1) {
            lines += listOf(currentString + argumentLines.first()) + argumentLines.drop(1).dropLast(1)
            currentString = argumentLines.last()
        } else {
            currentString += argumentLines.first()
        }
    }
    lines += ("$currentString)")
    return lines
}

private fun CallExpressionModel.translate(): String {
    return "${expression.translate()}${if (typeParameters.isEmpty()) "" else "<${typeParameters.joinToString(", ") { it.translate() }}>"}${
        "(${
            arguments.joinToString(
                    ", "
            ) { it.translate() }
        })"
    }"
}

private fun LiteralExpressionModel.translate(): String {
    return when (this) {
        is StringLiteralExpressionModel -> "\"$value\""
        is NumericLiteralExpressionModel -> value.toString()
        is BooleanLiteralExpressionModel -> value.toString()
        is NullLiteralExpressionModel -> "null"
        else -> raiseConcern("unknown LiteralExpressionModel ${this}") { "" }
    }
}

private fun TemplateTokenModel.translate(): String {
    return when (this) {
        is StringTemplateTokenModel -> value
        is ExpressionTemplateTokenModel -> "\${${expression.translate()}}"
        else -> raiseConcern("unknown TemplateTokenModel ${this}") { "" }
    }
}

private fun BinaryOperatorModel.translate(): String {
    return when (this) {
        PLUS -> "+"
        MINUS -> "-"
        MULT -> "*"
        DIV -> "/"
        MOD -> "%"
        ASSIGN -> "="
        PLUS_ASSIGN -> "+="
        MINUS_ASSIGN -> "-="
        MULT_ASSIGN -> "*="
        DIV_ASSIGN -> "/="
        MOD_ASSIGN -> "%="
        AND -> "&&"
        OR -> "||"
        EQ -> "=="
        NOT_EQ -> "!="
        REF_EQ -> "==="
        REF_NOT_EQ -> "!=="
        LT -> "<"
        GT -> ">"
        LE -> "<="
        GE -> ">="
        BITWISE_AND -> "and"
        BITWISE_OR -> "or"
        BITWISE_XOR -> "xor"
        SHIFT_LEFT -> "shl"
        SHIFT_RIGHT -> "shr"
        else -> raiseConcern("unable to process binaryOperatorModel $this") {
            ""
        }
    }
}

private fun UnaryOperatorModel.translate(): String {
    return when (this) {
        INCREMENT -> "++"
        DECREMENT -> "--"
        UNARY_PLUS -> "+"
        UNARY_MINUS -> "-"
        NOT -> "!"
        else -> raiseConcern("unable to process unaryOperatorModel $this") {
            ""
        }
    }
}

private fun ExpressionModel.translateMultiLine(): List<String> {
    return when (this) {
        is CallExpressionModel -> translateMultiLine()
        is LambdaExpressionModel -> translateMultiLine()
        is PropertyAccessExpressionModel -> {
            val left = left.translateMultiLine()
            val right = right.translateMultiLine()
            left.dropLast(1) + listOf("${left.last()}.${right.first()}") + right.drop(1)
        }
        is BinaryExpressionModel -> {
            val (firstLine, nextLines) = right.translateMultiLine().splitLines()
            listOf("${left.translate()} ${operator.translate()} $firstLine") + nextLines
        }
        else -> listOf(translate())
    }
}

private fun ExpressionModel.translate(): String {
    return when (this) {
        is IdentifierExpressionModel -> identifier.translate()
        is ThisExpressionModel -> "this"
        is SuperExpressionModel -> "super"
        is LiteralExpressionModel -> this.translate()
        is ParenthesizedExpressionModel -> {
            when (expression) {
                is ParenthesizedExpressionModel -> expression.translate()
                else -> "(${expression.translate()})"
            }
        }
        is TemplateExpressionModel -> "\"${tokens.map { it.translate() }.joinToString(separator = "")}\""
        is BinaryExpressionModel -> "${left.translate()} ${operator.translate()} ${right.translate()}"
        is PropertyAccessExpressionModel -> "${left.translate()}.${right.translate()}"
        is IndexExpressionModel -> "${array.translate()}[${index.translate()}]"
        is CallExpressionModel -> translate()
        is DefinedExternallyExpressionModel -> previousExpression?.translate().let {
           "${identifier.translate()}${if (it != null) " /* $it */" else ""}"
        }
        is UnaryExpressionModel -> if (isPrefix) {
            "${operator.translate()}${operand.translate()}"
        } else {
            "${operand.translate()}${operator.translate()}"
        }
        is ConditionalExpressionModel -> "if (${
            when (val condition = condition) {
                is ParenthesizedExpressionModel -> condition.expression.translate()
                else -> condition.translate()
            }
        }) ${whenTrue.translate()} else ${whenFalse.translate()}"
        is AsExpressionModel -> "${expression.translate()} as ${type.translate()}"
        is NonNullExpressionModel -> expression.translate()
        is LambdaExpressionModel -> {
            val arguments = if (parameters.isNotEmpty()) "${translateLambdaParameters(parameters)} -> " else ""
            "{ $arguments${body.statements.map { it.translateAsOneLine() }.joinToString(separator = "; ")} }"
        }
        is IsExpressionModel -> "${expression.translate()} is ${type.translate()}"
        else -> raiseConcern("unknown ExpressionModel ${this}") { "" }
    }
}

private fun <T> List<T>.splitLines() = Pair(this[0], drop(1))

private fun StatementModel.translate(): List<String> {
    return when (this) {
        is AssignmentStatementModel -> {
            val (firstLine, nextLines) = right.translateMultiLine().splitLines()
            listOf("${left.translate()} = $firstLine") + nextLines
        }
        is ReturnStatementModel -> {
            val (firstLine, nextLines) = (expression?.translateMultiLine() ?: listOf("")).splitLines()
            listOf("return $firstLine") + nextLines
        }
        is ThrowStatementModel -> {
            val (firstLine, nextLines) = expression.translateMultiLine().splitLines()
            listOf("throw $firstLine") + nextLines
        }
        is BreakStatementModel -> listOf("break")
        is ContinueStatementModel -> listOf("continue")
        is ExpressionStatementModel -> expression.translateMultiLine()
        is BlockStatementModel -> listOf("{") +
                statements.flatMap { it.translate() }.map { FORMAT_TAB + it } +
                listOf("}")
        is RunBlockStatementModel -> listOf("run {") +
                statements.flatMap { it.translate() }.map { FORMAT_TAB + it } +
                listOf("}")
        is IfStatementModel -> {
            val header = "if (${
                when (val condition = condition) {
                    is ParenthesizedExpressionModel -> condition.expression.translate()
                    else -> condition.translate()
                }
            }) {"
            val mainBranch = thenStatement.statements.flatMap { it.translate() }.map { FORMAT_TAB + it }
            val elseStatement = elseStatement
            when {
                elseStatement == null -> listOf(header) + mainBranch + listOf("}")
                elseStatement.statements.size == 1 && elseStatement.statements.first() is IfStatementModel -> {
                    val nestedIf = elseStatement.statements.first()
                    val translatedNestedIf = nestedIf.translate()
                    listOf(header) + mainBranch + listOf("} else ${translatedNestedIf.first()}") + translatedNestedIf.drop(1)
                }
                else -> {
                    val elseBranch = elseStatement.statements.flatMap { it.translate() }.map { FORMAT_TAB + it }
                    listOf(header) + mainBranch + listOf("} else {") + elseBranch + listOf("}")
                }
            }
        }
        is WhileStatementModel -> {
            val header = "while (${condition.translate()}) "
            val body = body.translate()
            return listOf(header + body[0]) + body.drop(1)
        }
        is ForInStatementModel -> {
            val header = "for (${variable.translate(asDeclaration = false)} in ${expression.translate()}) "
            val body = body.translate()
            return listOf(header + body[0]) + body.drop(1)
        }
        is WhenStatementModel -> {
            val header = "when (${expression.translate()}) {"
            val cases = cases.flatMap { case ->
                val caseHeader = "${case.condition?.joinToString { it.translate() } ?: "else"} -> "
                val body = case.body.translate()
                listOf(caseHeader + body[0]) + body.drop(1)
            }.map { FORMAT_TAB + it }
            return listOf(header) + cases + listOf("}")
        }
        is VariableModel -> {
            return listOf(translate())
        }
        else -> raiseConcern("unknown StatementModel ${this}") { listOf<String>() }
    }
}

private fun StatementModel.translateAsOneLine(): String {
    return when (this) {
        is BlockStatementModel -> "{ ${statements.joinToString(separator = "; ") { it.translateAsOneLine() }} }"
        is RunBlockStatementModel -> "run { ${statements.joinToString(separator = "; ") { it.translateAsOneLine() }} }"
        is IfStatementModel -> {
            val header = "if (${condition.translate()}) {"
            val mainBranch = thenStatement.statements.joinToString(separator = "; ") { it.translateAsOneLine() }
            val elseStatement = elseStatement
            when {
                elseStatement == null -> "$header $mainBranch }"
                elseStatement.statements.size == 1 && elseStatement.statements.first() is IfStatementModel -> {
                    val nestedIf = elseStatement.statements.first()
                    val translatedNestedIf = nestedIf.translateAsOneLine()
                    "$header $mainBranch } else $translatedNestedIf"
                }
                else -> {
                    val elseBranch = elseStatement.statements.joinToString(separator = "; ") { it.translateAsOneLine() }
                    "$header $mainBranch } else { $elseBranch }"
                }
            }
        }
        is WhileStatementModel -> {
            val header = "while (${condition.translate()}) "
            val body = body.translateAsOneLine()
            return "$header$body"
        }
        is ForInStatementModel -> {
            val header = "for (${variable.translate(asDeclaration = false)} in ${expression.translate()})"
            val body = body.translateAsOneLine()
            return "$header $body"
        }
        is WhenStatementModel -> {
            val header = "when (${expression.translate()}) {"
            val cases = cases.map { case ->
                val caseHeader = "${case.condition?.joinToString { it.translate() } ?: "else"} -> "
                val body = case.body.translateAsOneLine()
                "$caseHeader $body"
            }.joinToString(separator = "; ")
            return "$header $cases }"
        }
        else -> translate().joinToString(separator = " ")
    }
}

private fun ClassLikeReferenceModel.translate(): String {
    return name.translate() + if (typeParameters.isNotEmpty()) {
        "<${typeParameters.map { it.translate() }.joinToString(", ")}>"
    } else {
        ""
    }
}

private fun BlockStatementModel?.translate(padding: Int, output: (String) -> Unit) {
    if (this != null) {
        statements.forEach { statement ->
            statement.translate().forEach { statementLine ->
                output(
                        FORMAT_TAB.repeat(padding + 1) + statementLine
                )
            }
        }
        output(FORMAT_TAB.repeat(padding) + "}")
    }
}

private fun BlockStatementModel?.translateFirstLine(): String {
    return when {
        this == null -> ""
        statements.isEmpty() -> " { }"
        statements.size == 1 && statements.single().translate().size == 1 -> {
            if (statements.single() is ReturnStatementModel) {
                " = ${(statements.single() as ReturnStatementModel).expression?.translate()}"
            } else {
                " { ${statements.single().translate().single()} }"
            }
        }
        else -> " {"
    }
}

private fun FunctionModel.translate(padding: Int, output: (String) -> Unit) {
    comment?.translate(output)

    val returnsUnit = (type is TypeValueModel) &&
            (type as TypeValueModel).value == IdentifierEntity("Unit")

    val returnClause = if (returnsUnit) "" else ": ${type.translate()}"

    var typeParams = translateTypeParameters(typeParameters)
    if (typeParams.isNotEmpty()) {
        typeParams = " " + typeParams
    }

    val tokens = mutableListOf<String>()
    visibilityModifier.translate()?.let { tokens.add(it) }

    val modifier = if (inline) {
        KEYWORD_INLINE
    } else if (external) {
        KEYWORD_EXTERNAL
    } else null
    modifier?.let { tokens.add(modifier) }

    if (operator) {
        tokens.add(KEYWORD_OPERATOR)
    }

    val body = body

    val shouldBeTranslatedAsOneLine = body == null || body.statements.isEmpty() ||
            (body.statements.size == 1 && body.statements[0].translate().size <= 1)

    val bodyFirstLine = body.translateFirstLine()

    val funName = if (extend == null) {
        name.translate()
    } else {
        extend?.translate() + "." + name.translate()
    }

    tokens.add("fun")

    output(
            FORMAT_TAB.repeat(padding) +
                    "${translateAnnotations(annotations)}${tokens.joinToString(" ")}${typeParams} ${funName}(${
                        translateParameters(
                                parameters
                        )
                    })${returnClause}${type.translateMeta()}${bodyFirstLine}"
    )

    if (!shouldBeTranslatedAsOneLine) {
        body.translate(padding, output)
    }
}

private fun MethodModel.translate(): List<String> {
    val returnsUnit = (type is TypeValueModel) &&
            ((type as TypeValueModel).value == IdentifierEntity("@@None")
                    || (type as TypeValueModel).value == IdentifierEntity("Unit"))
    val returnClause = if (returnsUnit) "" else ": ${type.translate()}"

    var typeParams = translateTypeParameters(typeParameters)
    if (typeParams.isNotEmpty()) {
        typeParams = " " + typeParams
    }

    val operatorModifier = if (operator) "operator " else ""
    val annotations = annotations.map { it.translate() }

    val open = !static && open
    val overrideClause = if (!override.isNullOrEmpty()) "override " else if (open) "open " else ""

    val metaClause = type.translateMeta()

    val bodyFirstLine = body.translateFirstLine()

    val bodyOtherLines = mutableListOf<String>()

    val body = body

    if (body != null) {

        val shouldBeTranslatedAsOneLine = body.statements.isEmpty() ||
                (body.statements.size == 1 && body.statements[0].translate().size <= 1)

        if (!shouldBeTranslatedAsOneLine) {
            body.translate(0) { bodyOtherLines += it }
        }
    }

    return annotations +
            listOf(
                    "${overrideClause}${operatorModifier}fun${typeParams} ${name.translate()}(${
                        translateParameters(
                                parameters
                        )
                    })${returnClause}$metaClause${bodyFirstLine}"
            ) +
            bodyOtherLines
}

private fun ConstructorModel.translate(): List<String> {
    val typeParams = translateTypeParameters(typeParameters)
    return listOf("constructor${typeParams}(${translateParameters(parameters, false)})")
}

private fun InitBlockModel.translate(): List<String> {
    val (firstLine, nextLines) = body.translate().splitLines()
    return listOf("init $firstLine") + nextLines
}

private fun TypeAliasModel.translate(): String {
    return "typealias ${name.translate()}${translateTypeParameters(typeParameters)} = ${typeReference.translate()}"
}

private fun VariableModel.translate(asDeclaration: Boolean = true): String {
    val variableKeyword = if (asDeclaration) {
        if (immutable) "val" else "var"
    } else {
        null
    }
    val modifier = if (inline) KEYWORD_INLINE else if (external) KEYWORD_EXTERNAL else null

    val body = if (initializer != null) {
        " = ${initializer?.translate()}"
    } else if ((get != null) && (set != null)) {
        val getter = "get() = ${get?.translateAsOneLine()};"
        val setter = "set(value) { ${set?.translateAsOneLine()} }"
        " ${getter} ${setter}"
    } else if (get != null) {
        val getter = "get() = ${get?.translateAsOneLine()}"
        " ${getter}"
    } else ""

    val typeParams = if (typeParameters.isEmpty()) {
        ""
    } else {
        " ${translateTypeParameters(typeParameters)}"
    }

    val type = if (explicitlyDeclaredType) {
        ": ${type.translate()}${type.translateMeta()}"
    } else {
        ""
    }

    val varName = if (extend == null) {
        name.translate()
    } else {
        extend?.translate() + "." + name.translate()
    }

    val tokens = mutableListOf<String>()

    visibilityModifier.translate()?.let { tokens.add(it) }
    modifier?.let { tokens.add(it) }

    if (variableKeyword != null) {
        tokens.add(variableKeyword)
    }

    val space = if (tokens.isEmpty()) "" else " "
    return "${translateAnnotations(annotations)}${tokens.joinToString(" ")}$space${typeParams}${varName}${type}${body}"
}

private fun EnumModel.translate(padding: Int = 0): String {
    val modifiers = mutableListOf<String>()

    if (external) {
        modifiers.add(KEYWORD_EXTERNAL)
    }

    val space = if (modifiers.isEmpty()) "" else " "
    val res = mutableListOf("${modifiers.joinToString(" ")}${space}enum class ${name.translate()} {")
    val members = values.map { value ->
        val metaClause = if (value.meta.isEmpty()) "" else " /* = ${value.meta} */"
        "${FORMAT_TAB.repeat(padding + 1)}${value.value}${metaClause}"
    }

    res.add(members.joinToString(",${LINE_SEPARATOR}"))
    res.add("${FORMAT_TAB.repeat(padding)}}")

    return res.joinToString(LINE_SEPARATOR)
}

private fun PropertyModel.translate(): String {
    val open = !static && open
    val modifier = if (!override.isNullOrEmpty()) "override " else if (open) "open " else ""
    val lateinitModifier = if (lateinit) "lateinit " else ""
    val varModifier = if (immutable) "val" else "var"
    val initializer = initializer?.let { " = ${it.translate()}" } ?: ""
    val type = if (explicitlyDeclaredType) {
        ": ${type.translate()}${type.translateMeta()}"
    } else {
        ""
    }

    return "$modifier$lateinitModifier$varModifier ${name.translate()}$type$initializer"
}

private fun MemberModel.translate(): List<String> {
    return when (this) {
        is MethodModel -> translate()
        is PropertyModel -> listOf(translate())
        is ConstructorModel -> translate()
        is InitBlockModel -> translate()
        is ClassModel -> listOf(translate(1))
        is EnumModel -> listOf(translate(1))
        is InterfaceModel -> listOf(translate(1))
        else -> raiseConcern("can not translate MemberModel ${this}") { listOf("") }
    }
}

private fun ImportModel.translate(): String {
    return name.translate(false) + (asAlias?.let { " as ${it.value}" } ?: "")
}

private fun PropertyModel.translateSignature(): List<String> {
    val varModifier = if (immutable) "val" else "var"
    val overrideClause = if (!override.isNullOrEmpty()) "override " else ""


    var typeParams = translateTypeParameters(typeParameters)
    if (typeParams.isNotEmpty()) {
        typeParams = " " + typeParams
    }
    val metaClause = type.translateMeta()
    val res = mutableListOf(
            "${overrideClause}${varModifier}${typeParams} ${name.translate()}: ${type.translate()}${metaClause}"
    )
    if (getter) {
        res.add(FORMAT_TAB + "get() = definedExternally")
    }
    if (setter) {
        res.add(FORMAT_TAB + "set(value) = definedExternally")
    }
    return res
}

private fun MethodModel.translateSignature(): List<String> {
    var typeParams = translateTypeParameters(typeParameters)
    if (typeParams.isNotEmpty()) {
        typeParams = " " + typeParams
    }

    val operatorModifier = if (operator) "${KEYWORD_OPERATOR} " else ""
    val annotations = annotations.map { it.translate() }

    val returnsUnit = (type is TypeValueModel) && ((type as TypeValueModel).value == IdentifierEntity("Unit"))
    val returnClause = if (returnsUnit) "" else ": ${type.translate()}"
    val overrideClause = if (!override.isNullOrEmpty()) "override " else ""

    val metaClause = type.translateMeta()
    val methodNodeTranslation =
            "${overrideClause}${operatorModifier}fun${typeParams} ${name.translate()}(${translateParameters(parameters)})${returnClause}$metaClause"
    return annotations + listOf(methodNodeTranslation)
}

private fun MemberModel.translateSignature(): List<String> {
    return when (this) {
        is MethodModel -> translateSignature()
        is PropertyModel -> translateSignature()
        is ClassModel -> listOf(translate(1))
        is InterfaceModel -> listOf(translate(1))
        else -> raiseConcern("can not translate signature ${this}") { emptyList<String>() }
    }
}

private fun translateHeritagModels(parentEntities: List<HeritageModel>): String {
    val parents = if (parentEntities.isNotEmpty()) {
        " : " + parentEntities.map { parentEntity ->
            "${parentEntity.value.translate()}${translateTypeArguments(parentEntity.typeParams)}"
        }.joinToString(", ")
    } else ""

    return parents
}

private fun TypeModel.translateAsHeritageClause(): String {
    return when (this) {
        is FunctionTypeModel -> translate()
        is TypeValueModel -> {
            val typeParams = if (params.isEmpty()) {
                ""
            } else {
                "<${params.joinToString("::") { it.type.translateAsHeritageClause() }}>"
            }

            "${value}${typeParams}"
        }
        else -> ""
    }
}

private fun DelegationModel.translate(): String {
    return when (this) {
        is ClassModel -> name.translate()
        is ExternalDelegationModel -> "definedExternally"
        else -> ""
    }
}

private fun HeritageModel.translateAsHeritageClause(): String {
    val delegationClause = delegateTo?.let { " by ${it.translate()}" } ?: ""
    return "${value.translateAsHeritageClause()}${delegationClause}"
}

private fun ClassModel.translate(depth: Int): String {
    val res = mutableListOf<String>()
    translate(depth) { res.add(it) }
    return res.joinToString(LINE_SEPARATOR)
}

private fun VisibilityModifierModel.translate(): String? {
    return when (this) {
        VisibilityModifierModel.PUBLIC -> "public"
        VisibilityModifierModel.INTERNAL -> "internal"
        VisibilityModifierModel.PRIVATE -> "private"
        VisibilityModifierModel.PROTECTED -> "protected"
        VisibilityModifierModel.DEFAULT -> null
    }
}

private fun ClassModel.translate(depth: Int, output: (String) -> Unit) {
    val primaryConstructor = primaryConstructor
    val hasSecondaryConstructors = members.any { it is ConstructorModel }

    comment?.translate(output)

    val parents = translateHeritagModels(parentEntities)
    val modifiers = mutableListOf<String>()

    visibilityModifier.translate()?.let { modifiers.add(it) }

    if (external) {
        modifiers.add(KEYWORD_EXTERNAL)
    }

    val params = if (primaryConstructor == null) "" else
        if (primaryConstructor.parameters.isEmpty() && !hasSecondaryConstructors) "" else "(${
            translateParameters(
                    primaryConstructor.parameters
            )
        })"

    when (inheritanceModifier) {
        InheritanceModifierModel.ABSTRACT -> "abstract"
        InheritanceModifierModel.OPEN -> "open"
        InheritanceModifierModel.FINAL -> null
        InheritanceModifierModel.SEALED -> "sealed"
    }?.let {
        modifiers.add(it)
    }

    val classDeclaration =
            "${translateAnnotations(annotations)}${modifiers.joinToString(" ")} class ${name.translate()}${
                translateTypeParameters(
                        typeParameters
                )
            }${params}${parents}"

    val members = members
    val staticMembers = companionObject?.members.orEmpty()
    val companionObjectHeritages = translateHeritagModels(companionObject?.parentEntities.orEmpty())

    val hasMembers = members.isNotEmpty()
    val hasStaticMembers = staticMembers.isNotEmpty()
    val hasCompanionParent = companionObjectHeritages.isNotEmpty()
    val isBlock = hasMembers || hasStaticMembers || hasCompanionParent

    output(classDeclaration + if (isBlock) " {" else "")

    if (hasMembers) {
        members.flatMap { it.translate() }.map({ FORMAT_TAB.repeat(depth + 1) + it }).forEach {
            output(it)
        }
    }

    if (companionObject != null) {
        if (hasMembers) {
            output("")
        }
        output(FORMAT_TAB.repeat(depth + 1) + "companion object${companionObjectHeritages}${if (!hasStaticMembers) "" else " {"}")
    }
    if (hasStaticMembers) {
        staticMembers.flatMap { it.translate() }.map({ FORMAT_TAB.repeat(depth + 2) + it }).forEach {
            output(it)
        }
        output(FORMAT_TAB.repeat(depth + 1) + "}")
    }

    if (isBlock) {
        output(FORMAT_TAB.repeat(depth) + "}")
    }
}

private fun InterfaceModel.translate(padding: Int): String {
    val res = mutableListOf<String>()
    translate(padding) { res.add(it) }
    return res.joinToString(LINE_SEPARATOR)
}

fun InterfaceModel.translate(padding: Int, output: (String) -> Unit) {

    comment?.translate(output)

    val hasMembers = members.isNotEmpty()
    val staticMembers = companionObject?.members.orEmpty()

    val isBlock = hasMembers || staticMembers.isNotEmpty() || companionObject != null
    val parents = translateHeritagModels(parentEntities)

    val tokens = mutableListOf<String>()
    visibilityModifier.translate()?.let { tokens.add(it) }

    if (external) {
        tokens.add(KEYWORD_EXTERNAL)
    }

    tokens.add("interface")

    output(
            "${translateAnnotations(annotations)}${tokens.joinToString(" ")} ${name.translate()}${
                translateTypeParameters(
                        typeParameters
                )
            }${parents}" + if (isBlock) " {" else ""
    )
    if (isBlock) {
        members.flatMap { it.translateSignature() }.map { FORMAT_TAB.repeat(padding + 1) + it }.forEach { output(it) }

        if (companionObject != null) {
            val companionObjectParents = companionObject?.parentEntities
            val parentsResolved = if (!companionObjectParents.isNullOrEmpty()) {
                " : ${companionObjectParents.joinToString(", ") { it.translateAsHeritageClause() }}"
            } else {
                ""
            }

            if (hasMembers) {
                output("")
            }
            output("${FORMAT_TAB.repeat(padding + 1)}companion object${parentsResolved}${if (staticMembers.isEmpty()) "" else " {"}")

            if (staticMembers.isNotEmpty()) {
                staticMembers.flatMap { it.translate() }.map { "${FORMAT_TAB.repeat(padding + 2)}${it}" }
                        .forEach { output(it) }
                output("${FORMAT_TAB.repeat(padding + 1)}}")
            }
        }

        output("${FORMAT_TAB.repeat(padding)}}")
    }
}


class StringTranslator : ModelVisitor {
    private var myOutput: MutableList<String> = mutableListOf()

    private fun addOutput(fragment: String) {
        myOutput.add(fragment)
    }

    fun output(): String {
        return myOutput.joinToString(LINE_SEPARATOR)
    }

    override fun visitTypeAlias(typeAlias: TypeAliasModel) {
        addOutput("")
        addOutput(typeAlias.translate())
    }

    override fun visitVariable(variable: VariableModel) {
        addOutput("")
        addOutput(variable.translate())
    }

    override fun visitFunction(function: FunctionModel) {
        addOutput("")
        function.translate(0, ::addOutput)
    }

    override fun visitObject(objectNode: ObjectModel) {
        addOutput("")

        val modifiers = mutableListOf<String>()
        objectNode.visibilityModifier.translate()?.let { modifiers.add(it) }
        modifiers.add(KEYWORD_EXTERNAL)

        val objectModel =
                "${modifiers.joinToString(" ")} object ${objectNode.name.translate()}"

        val members = objectNode.members

        val hasMembers = members.isNotEmpty()

        addOutput(objectModel + " {")

        if (hasMembers) {
            members.flatMap { it.translate() }.map({ "    " + it }).forEach {
                addOutput(it)
            }

        }

        addOutput("}")
    }

    override fun visitEnum(enumNode: EnumModel) {
        addOutput("")
        addOutput(enumNode.translate())
    }

    override fun visitInterface(interfaceModel: InterfaceModel) {
        addOutput("")
        interfaceModel.translate(0, ::addOutput)
    }

    override fun visitClass(classModel: ClassModel) {
        addOutput("")
        classModel.translate(0, ::addOutput)
    }

    private fun visitImport(import: ImportModel) {
        addOutput("import ${import.translate()}")
    }

    override fun visitModule(moduleModel: ModuleModel) {
        if (moduleModel.declarations.isEmpty() && moduleModel.submodules.isEmpty()) {
            return
        }

        val containsSomethingExceptDocRoot = moduleModel.declarations.isNotEmpty()

        if (containsSomethingExceptDocRoot) {
            val translateAnnotations = translateAnnotations(moduleModel.annotations)

            if ((moduleModel.name != ROOT_PACKAGENAME)) {
                addOutput("${translateAnnotations}package ${moduleModel.name.translate(false)}")
                addOutput("")
            } else {
                if (translateAnnotations.isNotEmpty()) {
                    addOutput(translateAnnotations)
                }
            }
        }

        moduleModel.imports.forEachIndexed { _, importNode ->
            visitImport(importNode)
        }
    }

}