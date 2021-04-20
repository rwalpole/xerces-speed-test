package uk.gov.nationalarchives

import org.apache.commons.lang3.time.StopWatch
import org.slf4j.LoggerFactory

import java.net.URL
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import scala.util.Try

case class XsdValidator(xsdFile: URL) {

  private val logger = LoggerFactory.getLogger(classOf[XsdValidator])

  def validate(xmlFile: URL): Try[Unit] = Try {
    val stopWatch = new StopWatch
    stopWatch.start()
    logger.info("Starting validation..")
    val schemaFactory: SchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val schemaIn = xsdFile.openStream()
    stopWatch.split()
    logger.info(s"Ready to read schema after $stopWatch")
    val schema = schemaFactory.newSchema(new StreamSource(schemaIn))
    stopWatch.split()
    logger.info(s"Schema loaded after $stopWatch")
    val validator = schema.newValidator()
    stopWatch.split()
    logger.info(s"Validator created after $stopWatch")
    val in = xmlFile.openStream()
    validator.validate(new StreamSource(in))
    schemaIn.close()
    in.close()
    stopWatch.stop()
    logger.info(s"Validation took $stopWatch to complete")
  }
}
object XsdValidator extends App {
  val schemaFileUrl = new URL(args(0))
  val xmlFileUrl = new URL(args(1))
  val validator = XsdValidator(schemaFileUrl)
  validator.validate(xmlFileUrl)
}
