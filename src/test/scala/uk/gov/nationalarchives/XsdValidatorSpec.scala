package uk.gov.nationalarchives

import org.scalatest.TryValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.xml.sax.SAXParseException

import scala.util.Success

class XsdValidatorSpec extends AnyWordSpec with Matchers with TryValues {

  "The validate function" must {
    val schemaURL = getClass.getResource("/" + "eadbase.ent.xsd")
    val validator = XsdValidator(schemaURL)
    "return successfully when the XML file is valid" in {
       validator.validate(getClass.getResource("/" + "ead-valid.xml")) mustBe Success()
    }
    "return a SAXParseException when the XML file is invalid" in {
      validator.validate(getClass.getResource("/" + "ead-invalid.xml")).failure.exception mustBe a [SAXParseException]

    }
  }

}
