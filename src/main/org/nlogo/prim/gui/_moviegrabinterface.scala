// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.gui

import org.nlogo.api.Syntax
import org.nlogo.nvm.{ Command, Context, EngineException }
import org.nlogo.window.GUIWorkspace

class _moviegrabinterface extends Command {
  override def syntax =
    Syntax.commandSyntax
  override def perform(context: Context) {
    if (world.program.is3D)
      throw new EngineException(
          context, this, token.name + " is not supported in NetLogo 3D")
    workspace match {
      case gw: GUIWorkspace =>
        workspace.waitFor(
          new org.nlogo.api.CommandRunnable {
            def run() {
              try {
                if (gw.movieEncoder == null)
                  throw new EngineException(
                    context, _moviegrabinterface.this, "Must call MOVIE-START first")
                gw.movieEncoder.add(
                  org.nlogo.awt.Images.paintToImage(
                    gw.viewWidget.findWidgetContainer.asInstanceOf[java.awt.Component]))
              }
              catch {
                case ex: java.io.IOException =>
                  throw new EngineException(
                    context, _moviegrabinterface.this, ex.getMessage)
              }}})
      case _ =>
        throw new EngineException(
          context, this, token.name + " can only be used in the GUI")
    }
    context.ip = next
  }
}
