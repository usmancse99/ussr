/* This file was generated by SableCC (http://www.sablecc.org/). */

package dcd.highlevel.rdcd.parser.analysis;

import dcd.highlevel.rdcd.parser.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAGoal(AGoal node);
    void caseAIntegerLiteralLiteral(AIntegerLiteralLiteral node);
    void caseABooleanLiteralLiteral(ABooleanLiteralLiteral node);
    void caseATrueBooleanLiteral(ATrueBooleanLiteral node);
    void caseAFalseBooleanLiteral(AFalseBooleanLiteral node);
    void caseADecimalIntegerLiteral(ADecimalIntegerLiteral node);
    void caseAHexIntegerLiteral(AHexIntegerLiteral node);
    void caseAOctalIntegerLiteral(AOctalIntegerLiteral node);
    void caseASimpleNameName(ASimpleNameName node);
    void caseASimpleName(ASimpleName node);
    void caseAExternalName(AExternalName node);
    void caseACompilationUnit(ACompilationUnit node);
    void caseADeploymentSpec(ADeploymentSpec node);
    void caseARoleDeclaration(ARoleDeclaration node);
    void caseAAbstractConstant(AAbstractConstant node);
    void caseAConcreteIntConstant(AConcreteIntConstant node);
    void caseAConcreteNameConstant(AConcreteNameConstant node);
    void caseAInvariant(AInvariant node);
    void caseAVariableExp(AVariableExp node);
    void caseABinexpExp(ABinexpExp node);
    void caseAAdditionBinaryExp(AAdditionBinaryExp node);
    void caseAEqualityBinaryExp(AEqualityBinaryExp node);
    void caseALessThanBinaryExp(ALessThanBinaryExp node);
    void caseAGreaterThanBinaryExp(AGreaterThanBinaryExp node);

    void caseTWhiteSpace(TWhiteSpace node);
    void caseTTraditionalComment(TTraditionalComment node);
    void caseTDocumentationComment(TDocumentationComment node);
    void caseTEndOfLineComment(TEndOfLineComment node);
    void caseTAbstract(TAbstract node);
    void caseTIf(TIf node);
    void caseTRole(TRole node);
    void caseTEvent(TEvent node);
    void caseTExtends(TExtends node);
    void caseTDeployment(TDeployment node);
    void caseTDollar(TDollar node);
    void caseTTrue(TTrue node);
    void caseTFalse(TFalse node);
    void caseTLParenthese(TLParenthese node);
    void caseTRParenthese(TRParenthese node);
    void caseTLBrace(TLBrace node);
    void caseTRBrace(TRBrace node);
    void caseTLBracket(TLBracket node);
    void caseTRBracket(TRBracket node);
    void caseTSemicolon(TSemicolon node);
    void caseTComma(TComma node);
    void caseTDot(TDot node);
    void caseTAssign(TAssign node);
    void caseTLt(TLt node);
    void caseTGt(TGt node);
    void caseTComplement(TComplement node);
    void caseTBitComplement(TBitComplement node);
    void caseTQuestion(TQuestion node);
    void caseTColon(TColon node);
    void caseTEq(TEq node);
    void caseTLteq(TLteq node);
    void caseTGteq(TGteq node);
    void caseTNeq(TNeq node);
    void caseTAnd(TAnd node);
    void caseTOr(TOr node);
    void caseTPlusPlus(TPlusPlus node);
    void caseTMinusMinus(TMinusMinus node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTStar(TStar node);
    void caseTDiv(TDiv node);
    void caseTBitAnd(TBitAnd node);
    void caseTBitOr(TBitOr node);
    void caseTBitXor(TBitXor node);
    void caseTMod(TMod node);
    void caseTShiftLeft(TShiftLeft node);
    void caseTSignedShiftRight(TSignedShiftRight node);
    void caseTUnsignedShiftRight(TUnsignedShiftRight node);
    void caseTPlusAssign(TPlusAssign node);
    void caseTMinusAssign(TMinusAssign node);
    void caseTStarAssign(TStarAssign node);
    void caseTDivAssign(TDivAssign node);
    void caseTBitAndAssign(TBitAndAssign node);
    void caseTBitOrAssign(TBitOrAssign node);
    void caseTBitXorAssign(TBitXorAssign node);
    void caseTModAssign(TModAssign node);
    void caseTShiftLeftAssign(TShiftLeftAssign node);
    void caseTSignedShiftRightAssign(TSignedShiftRightAssign node);
    void caseTUnsignedShiftRightAssign(TUnsignedShiftRightAssign node);
    void caseTDecimalIntegerLiteral(TDecimalIntegerLiteral node);
    void caseTHexIntegerLiteral(THexIntegerLiteral node);
    void caseTOctalIntegerLiteral(TOctalIntegerLiteral node);
    void caseTFloatingPointLiteral(TFloatingPointLiteral node);
    void caseTCharacterLiteral(TCharacterLiteral node);
    void caseTStringLiteral(TStringLiteral node);
    void caseTIdentifier(TIdentifier node);
    void caseEOF(EOF node);
}
