// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.lex

import org.scalatest.FunSuite
import TokenMapper2D._

class TokenMapperTests extends FunSuite {
  test("OneCommand1") { expect("_fd")(getCommand("FD").toString) }
  test("OneCommand2") { expect("_fd")(getCommand("fd").toString) }
  test("BadCommand") { intercept[NoSuchElementException] { getCommand("gkhgjkh") } }

  test("OneReporter1") { expect("_timer")(getReporter("TIMER").toString) }
  test("OneReporter2") { expect("_timer")(getReporter("timer").toString) }
  test("BadReporter") { intercept[NoSuchElementException] { getReporter("gkhgjkh") } }

  test("OneKeyword1") { assert(isKeyword("to")) }
  test("OneKeyword2") { assert(isKeyword("TO")) }

  test("BadConstant") { assert(!isConstant("fnord666")) }
  test("BadConstantException") { intercept[NoSuchElementException] { getConstant("fnord666") } }
  test("OneIsConstant1") { assert(isConstant("FALSE")) }
  test("OneIsConstant2") { assert(isConstant("false")) }
  test("OneConstant1") { expect(java.lang.Boolean.FALSE)(getConstant("FALSE")) }
  test("OneConstant2") { expect(java.lang.Boolean.FALSE)(getConstant("false")) }
  test("ColorConstant1") { expect(105d)(getConstant("blue")) }
  test("ColorConstant2") { expect(105d)(getConstant("BLUE")) }
  test("GrayAndGrey") { expect(getConstant("grey"))(getConstant("GRAY")) }

  test("Removed1") { assert(!wasRemoved("random")) }
  test("Removed2") { assert(wasRemoved("random-or-random-float")) }

  test("reporter1") { assert(isReporter("random")) }
  test("reporter2") { assert(!isReporter("fd")) }
  test("reporter3") { assert(!isReporter("gkhgkj")) }
  test("reporter4") { assert(isReporter("random-or-random-float")) }

  test("command1") { assert(isCommand("fd")) }
  test("command2") { assert(isCommand("forward")) }
  test("command3") { assert(!isCommand("random")) }
  test("command4") { assert(!isCommand("kjhgkfjg")) }
  test("command5") { assert(!isCommand("random-or-random-float")) }

  test("patch2D") {
    expect("org.nlogo.prim._patch")(
      TokenMapper2D.getReporter("patch").getClass.getName)
  }
  test("patch3D") {
    expect("org.nlogo.prim.threed._patch")(
      TokenMapper3D.getReporter("patch").getClass.getName)
  }
}
