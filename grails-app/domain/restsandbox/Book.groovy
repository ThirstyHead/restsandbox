package restsandbox

import grails.rest.*

@Resource(uri="/books")
class Book {
  String title
  String author

  static constraints = {
  }
}
