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
      // NOTE: fails due to application/json(;charset=UTF-8)
      // response.contentType == JSON_CONTENT_TYPE
      response.contentType.contains("json")

      // NOTE: reading from this test's expected HashMap
      response.json.each{ pub ->
        assert pub.nickname == expected[pub.id]
      }

      // NOTE: reading from Publisher mock
      response.json.each{ pub ->
        assert pub.name == Publisher.get(pub.id).name
      }
    }



    void "test GET item"() {
      when:
      params.id = "orl"
      controller.show()

      then:
      println response.text
    }




}
