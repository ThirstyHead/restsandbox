package restsandbox

import grails.rest.*

// gives me:
//  class BookController extends RestfulController
//  also in UrlMappings: "/books"(resources:'book')

@Resource(uri="/books")
class Book {
  String title
  String author

  static constraints = {
  }
}
