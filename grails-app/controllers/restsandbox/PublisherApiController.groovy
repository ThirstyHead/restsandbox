package restsandbox

import grails.rest.*

// NOTE: Handles RESTful API

//@Transactional(readOnly = true)
class PublisherApiController extends RestfulController{
  static responseFormats = ['json', 'xml']
  // namespace != package, but they should match anyway
  // static namespace = "v1" 


  PublisherApiController() {
    super(Publisher)
  }


  // NOTE: Overrides standard int / primary key URI
  @Override
  protected Publisher queryForResource(Serializable id) {
      // where == compile time
      Publisher.where {
          nickname == id
      }.find()
  }


}