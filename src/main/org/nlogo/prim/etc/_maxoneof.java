// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.etc;

import java.util.ArrayList;
import java.util.List;

import org.nlogo.agent.Agent;
import org.nlogo.agent.AgentSet;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Reporter;
import org.nlogo.api.Syntax;

public final strictfp class _maxoneof
    extends Reporter {
  @Override
  public Syntax syntax() {
    int[] right = {Syntax.AgentsetType(), Syntax.NumberBlockType()};
    int ret = Syntax.AgentType();
    return Syntax.reporterSyntax(right, ret, "OTPL", "?");
  }

  @Override
  public Object report(final org.nlogo.nvm.Context context) throws LogoException {
    AgentSet sourceSet = argEvalAgentSet(context, 0);
    args[1].checkAgentSetClass(sourceSet, context);
    double winningValue = -Double.MAX_VALUE;
    List<Agent> winners = new ArrayList<Agent>();
    org.nlogo.nvm.Context freshContext =
        new org.nlogo.nvm.Context(context, sourceSet);
    for (AgentSet.Iterator iter = sourceSet.iterator(); iter.hasNext();) {
      org.nlogo.agent.Agent tester = iter.next();
      Object result = freshContext.evaluateReporter(tester, args[1]);
      if (!(result instanceof Double)) {
        continue;
      }
      double dvalue = ((Double) result).doubleValue();
      // need to be careful here to handle properly the case where
      // dvalue equals - Double.MAX_VALUE - ST 10/11/04
      if (dvalue >= winningValue) {
        if (dvalue > winningValue) {
          winningValue = dvalue;
          winners.clear();
        }
        winners.add(tester);
      }
    }
    if (winners.isEmpty()) {
      return org.nlogo.api.Nobody$.MODULE$;
    } else {
      return winners.get(context.job.random.nextInt(winners.size()));
    }
  }
}
