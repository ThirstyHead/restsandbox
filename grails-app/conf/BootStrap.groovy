import restsandbox.*

class BootStrap {

  def init = { servletContext ->
    environments{
      development{
        // Books
        def b1 = new Book(title:"Definitive Guide to Grails", 
                          author:"Graeme Rocher, Jeff Brown")
        b1.save(failOnError:true)

        def b2 = new Book(title:"Grails in Action", 
                          author:"Glen Smith, Peter Ledbrook")
        b2.save(failOnError:true)

        def b3 = new Book(title:"Grails 2: A Quick-Start Guide", 
                          author:"Dave Klein, Ben Klein")
        b3.save(failOnError:true)

        // Publishers
        def p1 = new Publisher(name:"Pragmatic Programmers", 
                               nickname:"prag")
        p1.save(failOnError:true)

        def p2 = new Publisher(name:"O'Reilly", 
                               nickname:"orl")
        p2.save(failOnError:true)

      }
    }

  }
  def destroy = {
  }
}
