package controllers

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import org.specs2.specification.BeforeSpec
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.core.Fragments
import play.api.mvc.AnyContentAsEmpty
import play.api.test.Helpers._
import play.api.test._

@RunWith(classOf[JUnitRunner])
class FrontendControllerSpec extends Specification with Mockito with BeforeSpec {
  /**
    * This spec assumes the file `/public/index.html` either doesn't exist, or exists and has these exact contents.
    * This can change if the `sbt dist` command is ever executed
    */
  val testHtmlPath = "public/index.html"
  val testHtml = "<html><head></head><body><h1>Test</h1></body></html>"

  // Ensure the required test file exists or is created
  @Override
  override def beforeSpec: Fragments = {
    if (testFileExists) success
    else {
      createTestFie
      success
    }
  }

  "FrontendController" should {
    "render the index from the application" in new WithApplication {
      val controller = app.injector.instanceOf[FrontendController]
      val index = controller.index().apply(getRequest("/"))

      status(index) must beEqualTo(OK)
      contentType(index) must beSome("text/html")
      val html = contentAsString(index)
      html must beEqualTo(testHtml)
    }

    "fallback to the index from the router when requesting invalid path" in new WithApplication {
      val request = getRequest("/foo")
      val index = route(app, request).get

      status(index) must beEqualTo(OK)
      contentType(index) must beSome("text/html")
      val html = contentAsString(index)
      html must beEqualTo(testHtml)
    }

    "error page from the router when requesting invalid path" in new WithApplication {
      val request = getRequest("/foo.bar")
      val apiFoo = route(app, request).get

      status(apiFoo) must beEqualTo(NOT_FOUND)
      contentType(apiFoo) must beSome("text/html")
      val errorHtml = contentAsString(apiFoo)
      errorHtml must contain("Action Not Found")
    }

    "error page from the router when requesting invalid api path" in new WithApplication {
      val request = getRequest("/api/foo")
      val apiFoo = route(app, request).get

      status(apiFoo) must beEqualTo(NOT_FOUND)
      contentType(apiFoo) must beSome("text/html")
      val errorHtml = contentAsString(apiFoo)
      errorHtml must contain("Action Not Found")
    }

  }

  private def getRequest(uri: String): FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, uri).withHeaders("User-Agent" -> "Foobar")

  private def testFileExists: Boolean = {
    val contents = if (Files.exists(Paths.get(testHtmlPath))) {
      val testTxtSource = scala.io.Source.fromFile("public/index.html")
      val contents = testTxtSource.mkString
      testTxtSource.close()
      contents
    }
    testHtml equals contents
  }

  private def createTestFie: Boolean = {
    new File(testHtmlPath.split(File.separator).head).mkdirs
    Files.write(Paths.get("public/index.html"), testHtml.getBytes(StandardCharsets.UTF_8))
    testFileExists
  }
}
