// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.etc;

import org.nlogo.api.LogoException;
import org.nlogo.nvm.Reporter;
import org.nlogo.api.Syntax;
import org.nlogo.nvm.Context;

public final strictfp class _randomfloat extends Reporter {
  @Override
  public Syntax syntax() {
    return Syntax.reporterSyntax
        (new int[]{Syntax.NumberType()},
            Syntax.NumberType());
  }

  @Override
  public Object report(Context context) throws LogoException {
    return report_1(context, argEvalDoubleValue(context, 0));
  }

  public double report_1(Context context, double d) {
    return d * context.job.random.nextDouble();
  }
}
