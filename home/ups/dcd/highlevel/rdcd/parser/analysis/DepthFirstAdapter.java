/* This file was generated by SableCC (http://www.sablecc.org/). */

package dcd.highlevel.rdcd.parser.analysis;

import java.util.*;
import dcd.highlevel.rdcd.parser.node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    public void defaultOut(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node)
    {
        inStart(node);
        node.getPGoal().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAGoal(AGoal node)
    {
        defaultIn(node);
    }

    public void outAGoal(AGoal node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGoal(AGoal node)
    {
        inAGoal(node);
        if(node.getCompilationUnit() != null)
        {
            node.getCompilationUnit().apply(this);
        }
        outAGoal(node);
    }

    public void inAIntegerLiteralLiteral(AIntegerLiteralLiteral node)
    {
        defaultIn(node);
    }

    public void outAIntegerLiteralLiteral(AIntegerLiteralLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntegerLiteralLiteral(AIntegerLiteralLiteral node)
    {
        inAIntegerLiteralLiteral(node);
        if(node.getIntegerLiteral() != null)
        {
            node.getIntegerLiteral().apply(this);
        }
        outAIntegerLiteralLiteral(node);
    }

    public void inABooleanLiteralLiteral(ABooleanLiteralLiteral node)
    {
        defaultIn(node);
    }

    public void outABooleanLiteralLiteral(ABooleanLiteralLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABooleanLiteralLiteral(ABooleanLiteralLiteral node)
    {
        inABooleanLiteralLiteral(node);
        if(node.getBooleanLiteral() != null)
        {
            node.getBooleanLiteral().apply(this);
        }
        outABooleanLiteralLiteral(node);
    }

    public void inATrueBooleanLiteral(ATrueBooleanLiteral node)
    {
        defaultIn(node);
    }

    public void outATrueBooleanLiteral(ATrueBooleanLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseATrueBooleanLiteral(ATrueBooleanLiteral node)
    {
        inATrueBooleanLiteral(node);
        if(node.getTrue() != null)
        {
            node.getTrue().apply(this);
        }
        outATrueBooleanLiteral(node);
    }

    public void inAFalseBooleanLiteral(AFalseBooleanLiteral node)
    {
        defaultIn(node);
    }

    public void outAFalseBooleanLiteral(AFalseBooleanLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFalseBooleanLiteral(AFalseBooleanLiteral node)
    {
        inAFalseBooleanLiteral(node);
        if(node.getFalse() != null)
        {
            node.getFalse().apply(this);
        }
        outAFalseBooleanLiteral(node);
    }

    public void inADecimalIntegerLiteral(ADecimalIntegerLiteral node)
    {
        defaultIn(node);
    }

    public void outADecimalIntegerLiteral(ADecimalIntegerLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADecimalIntegerLiteral(ADecimalIntegerLiteral node)
    {
        inADecimalIntegerLiteral(node);
        if(node.getDecimalIntegerLiteral() != null)
        {
            node.getDecimalIntegerLiteral().apply(this);
        }
        outADecimalIntegerLiteral(node);
    }

    public void inAHexIntegerLiteral(AHexIntegerLiteral node)
    {
        defaultIn(node);
    }

    public void outAHexIntegerLiteral(AHexIntegerLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAHexIntegerLiteral(AHexIntegerLiteral node)
    {
        inAHexIntegerLiteral(node);
        if(node.getHexIntegerLiteral() != null)
        {
            node.getHexIntegerLiteral().apply(this);
        }
        outAHexIntegerLiteral(node);
    }

    public void inAOctalIntegerLiteral(AOctalIntegerLiteral node)
    {
        defaultIn(node);
    }

    public void outAOctalIntegerLiteral(AOctalIntegerLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAOctalIntegerLiteral(AOctalIntegerLiteral node)
    {
        inAOctalIntegerLiteral(node);
        if(node.getOctalIntegerLiteral() != null)
        {
            node.getOctalIntegerLiteral().apply(this);
        }
        outAOctalIntegerLiteral(node);
    }

    public void inASimpleNameName(ASimpleNameName node)
    {
        defaultIn(node);
    }

    public void outASimpleNameName(ASimpleNameName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASimpleNameName(ASimpleNameName node)
    {
        inASimpleNameName(node);
        if(node.getSimpleName() != null)
        {
            node.getSimpleName().apply(this);
        }
        outASimpleNameName(node);
    }

    public void inASimpleName(ASimpleName node)
    {
        defaultIn(node);
    }

    public void outASimpleName(ASimpleName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASimpleName(ASimpleName node)
    {
        inASimpleName(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outASimpleName(node);
    }

    public void inAExternalName(AExternalName node)
    {
        defaultIn(node);
    }

    public void outAExternalName(AExternalName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAExternalName(AExternalName node)
    {
        inAExternalName(node);
        if(node.getDollar() != null)
        {
            node.getDollar().apply(this);
        }
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAExternalName(node);
    }

    public void inACompilationUnit(ACompilationUnit node)
    {
        defaultIn(node);
    }

    public void outACompilationUnit(ACompilationUnit node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACompilationUnit(ACompilationUnit node)
    {
        inACompilationUnit(node);
        {
            List<PRoleDeclaration> copy = new ArrayList<PRoleDeclaration>(node.getRoleDeclaration());
            for(PRoleDeclaration e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getDeploymentSpec() != null)
        {
            node.getDeploymentSpec().apply(this);
        }
        outACompilationUnit(node);
    }

    public void inADeploymentSpec(ADeploymentSpec node)
    {
        defaultIn(node);
    }

    public void outADeploymentSpec(ADeploymentSpec node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADeploymentSpec(ADeploymentSpec node)
    {
        inADeploymentSpec(node);
        if(node.getDeployment() != null)
        {
            node.getDeployment().apply(this);
        }
        if(node.getLBrace() != null)
        {
            node.getLBrace().apply(this);
        }
        {
            List<TIdentifier> copy = new ArrayList<TIdentifier>(node.getIdentifier());
            for(TIdentifier e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getRBrace() != null)
        {
            node.getRBrace().apply(this);
        }
        outADeploymentSpec(node);
    }

    public void inARoleDeclaration(ARoleDeclaration node)
    {
        defaultIn(node);
    }

    public void outARoleDeclaration(ARoleDeclaration node)
    {
        defaultOut(node);
    }

    @Override
    public void caseARoleDeclaration(ARoleDeclaration node)
    {
        inARoleDeclaration(node);
        if(node.getRole() != null)
        {
            node.getRole().apply(this);
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getExtends() != null)
        {
            node.getExtends().apply(this);
        }
        if(node.getSuper() != null)
        {
            node.getSuper().apply(this);
        }
        if(node.getLBrace() != null)
        {
            node.getLBrace().apply(this);
        }
        {
            List<PConstant> copy = new ArrayList<PConstant>(node.getConstant());
            for(PConstant e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PInvariant> copy = new ArrayList<PInvariant>(node.getInvariant());
            for(PInvariant e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getRBrace() != null)
        {
            node.getRBrace().apply(this);
        }
        outARoleDeclaration(node);
    }

    public void inAAbstractConstant(AAbstractConstant node)
    {
        defaultIn(node);
    }

    public void outAAbstractConstant(AAbstractConstant node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAbstractConstant(AAbstractConstant node)
    {
        inAAbstractConstant(node);
        if(node.getAbstract() != null)
        {
            node.getAbstract().apply(this);
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAAbstractConstant(node);
    }

    public void inAConcreteIntConstant(AConcreteIntConstant node)
    {
        defaultIn(node);
    }

    public void outAConcreteIntConstant(AConcreteIntConstant node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAConcreteIntConstant(AConcreteIntConstant node)
    {
        inAConcreteIntConstant(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getAssign() != null)
        {
            node.getAssign().apply(this);
        }
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAConcreteIntConstant(node);
    }

    public void inAConcreteNameConstant(AConcreteNameConstant node)
    {
        defaultIn(node);
    }

    public void outAConcreteNameConstant(AConcreteNameConstant node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAConcreteNameConstant(AConcreteNameConstant node)
    {
        inAConcreteNameConstant(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getAssign() != null)
        {
            node.getAssign().apply(this);
        }
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAConcreteNameConstant(node);
    }

    public void inAInvariant(AInvariant node)
    {
        defaultIn(node);
    }

    public void outAInvariant(AInvariant node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAInvariant(AInvariant node)
    {
        inAInvariant(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAInvariant(node);
    }

    public void inAVariableExp(AVariableExp node)
    {
        defaultIn(node);
    }

    public void outAVariableExp(AVariableExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVariableExp(AVariableExp node)
    {
        inAVariableExp(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAVariableExp(node);
    }

    public void inABinexpExp(ABinexpExp node)
    {
        defaultIn(node);
    }

    public void outABinexpExp(ABinexpExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABinexpExp(ABinexpExp node)
    {
        inABinexpExp(node);
        if(node.getLParenthese() != null)
        {
            node.getLParenthese().apply(this);
        }
        if(node.getBinaryExp() != null)
        {
            node.getBinaryExp().apply(this);
        }
        if(node.getRParenthese() != null)
        {
            node.getRParenthese().apply(this);
        }
        outABinexpExp(node);
    }

    public void inAAdditionBinaryExp(AAdditionBinaryExp node)
    {
        defaultIn(node);
    }

    public void outAAdditionBinaryExp(AAdditionBinaryExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAdditionBinaryExp(AAdditionBinaryExp node)
    {
        inAAdditionBinaryExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getPlus() != null)
        {
            node.getPlus().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAAdditionBinaryExp(node);
    }

    public void inAEqualityBinaryExp(AEqualityBinaryExp node)
    {
        defaultIn(node);
    }

    public void outAEqualityBinaryExp(AEqualityBinaryExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEqualityBinaryExp(AEqualityBinaryExp node)
    {
        inAEqualityBinaryExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getEq() != null)
        {
            node.getEq().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAEqualityBinaryExp(node);
    }

    public void inALessThanBinaryExp(ALessThanBinaryExp node)
    {
        defaultIn(node);
    }

    public void outALessThanBinaryExp(ALessThanBinaryExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALessThanBinaryExp(ALessThanBinaryExp node)
    {
        inALessThanBinaryExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getLt() != null)
        {
            node.getLt().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outALessThanBinaryExp(node);
    }

    public void inAGreaterThanBinaryExp(AGreaterThanBinaryExp node)
    {
        defaultIn(node);
    }

    public void outAGreaterThanBinaryExp(AGreaterThanBinaryExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGreaterThanBinaryExp(AGreaterThanBinaryExp node)
    {
        inAGreaterThanBinaryExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        if(node.getGt() != null)
        {
            node.getGt().apply(this);
        }
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAGreaterThanBinaryExp(node);
    }
}
