// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim;

import org.nlogo.agent.AgentSet;
import org.nlogo.agent.Link;
import org.nlogo.agent.Turtle;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.api.Syntax;

public final strictfp class _inlinkfrom
    extends Reporter {
  private final String breedName;

  public _inlinkfrom() {
    breedName = null;
  }

  public _inlinkfrom(String breedName) {
    this.breedName = breedName;
  }

  @Override
  public Syntax syntax() {
    return Syntax.reporterSyntax
        (new int[]{Syntax.AgentType()},
            Syntax.AgentType(), "-T--");
  }

  @Override
  public Object report(final Context context)
      throws LogoException {
    org.nlogo.agent.LinkManager linkManager = world.linkManager;
    AgentSet breed = breedName == null ? world.links() : world.getLinkBreed(breedName);
    mustNotBeUndirected(breed, context);
    Turtle target = argEvalTurtle(context, 0);
    Link link = linkManager.findLinkFrom(target, (Turtle) context.agent, breed, true);
    if (link == null) {
      return org.nlogo.api.Nobody$.MODULE$;
    }
    return link;
  }
}
