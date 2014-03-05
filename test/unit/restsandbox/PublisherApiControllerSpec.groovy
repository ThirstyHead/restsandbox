package restsandbox

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin


/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PublisherApiController)
@Mock(Publisher)
class PublisherApiControllerSpec extends Specification {

    def setup() {
      def p1 = new Publisher(name:"Pragmatic Programmers", 
                             nickname:"prag")
      p1.save(failOnError:true)

      def p2 = new Publisher(name:"O'Reilly", 
                             nickname:"orl")
      p2.save(failOnError:true)

      assert Publisher.count() == 2
    }

    def cleanup() {
    }

    /**
      response == org.springframework.mock.web.MockHttpServletResponse
      http://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/mock/web/MockHttpServletResponse.html

      response.json == org.codehaus.groovy.grails.web.json.JSONElement
      http://grails.org/doc/latest/api/org/codehaus/groovy/grails/web/json/JSONObject.html
     */
    void "test GET index.json"() {
      given:
      def expected = [1:"prag", 2:"orl"]

      when:
      controller.index()

      then:
      println "response.text:"
      println response.text
      println "-" * 25
      println "response.status:"
      println response.status
      println "-" * 25
      println "response.contentType:"
      println response.contentType

      response.json.size() == 2
      response.status == 200
      // NOTE: fails due to actual response: application/json(;charset=UTF-8)
      // response.contentType == JSON_CONTENT_TYPE
      response.contentType.contains("json")
      response.contentType.startsWith("application/json")

      // NOTE: reading from this test's expected HashMap
      response.json.each{ pub ->
        assert pub.nickname == expected[pub.id]
      }

      // NOTE: reading from Publisher mock
      response.json.each{ pub ->
        assert pub.name == Publisher.get(pub.id).name
      }
    }



    /**
      request == org.springframework.mock.web.MockHttpServletRequest
      http://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/mock/web/MockHttpServletRequest.html
     
      response.xml == groovy.util.XmlSlurper
      http://groovy.codehaus.org/api/groovy/util/XmlSlurper.html
     */
    void "test GET index.xml"() {
      when:
      request.method = "GET"
      params.format = "xml"
      controller.index()

      // NOTE: didn't work; still returns JSON
      //request.contentType = XML_CONTENT_TYPE
      //request.contentType = "text/xml"

      // NOTE: this triggers an XML response: application/xml(;charset=UTF-8)
      //request.addHeader("Accept", "text/xml")



      then:
      println "response.text:"
      println response.text
      println "-" * 25
      println "response.status:"
      println response.status
      println "-" * 25
      println "response.contentType:"
      println response.contentType


      // NOTE: XML API is slightly different than request.json.size()
      response.xml.children().size() == 2
      response.status == 200
      // NOTE: fails due to actual response: application/xml(;charset=UTF-8)
      // response.contentType == XML_CONTENT_TYPE
      response.contentType.contains("xml")
      response.contentType.startsWith("text/xml")

      response.xml.children().each{
        assert it.nickname in ["orl", "prag"]
      }


      // <publisher id="1">
      //   <name>Pragmatic Programmers</name>
      //   <nickname>prag</nickname>
      // </publisher>
      response.xml.publisher.each{ pub ->
        // NOTE: pub.@id returns the attribute 'id'
        // NOTE: need to coerce the String into an int
        def id = pub.@id.toInteger()
        def expected = Publisher.get(id)
        assert pub.@id == expected.id
        assert pub.name == expected.name
        assert pub.nickname == expected.nickname
      }

      response.xml.publisher.each{ pub ->
        def expected = Publisher.findByNickname(pub.nickname)
        assert pub.name == expected.name
      }
    }






    void "test GET item.json"() {
      when:
      params.id = "orl"
      controller.show()

      then:
      println response.text

      response.json.id == 2
      response.json.name == "O'Reilly"
      response.json.nickname == "orl"
    }




}
