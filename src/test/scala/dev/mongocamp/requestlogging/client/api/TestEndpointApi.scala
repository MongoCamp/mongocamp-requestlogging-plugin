/**
 * mongocamp-server
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.4.2
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package dev.mongocamp.requestlogging.client.api

import dev.mongocamp.server.test.TestServer
import dev.mongocamp.server.test.client.core.JsonSupport._
import dev.mongocamp.server.test.client.model.JsonValueBoolean
import sttp.client3._
import sttp.model.Method

object TestEndpointApi {

def apply(baseUrl: String = TestServer.serverBaseUrl) = new TestEndpointApi(baseUrl)
}

class TestEndpointApi(baseUrl: String) {

  /**
   * Test Request with 5s blocking
   * 
   * Expected answers:
   *   code 200 : JsonValueBoolean ()
   *   code 0 : ErrorDescription ()
   *              Headers :
   *                x-error-code - Error Code
   *                x-error-message - Message of the MongoCampException
   *                x-error-additional-info - Additional information for the MongoCampException
   * 
   * Available security schemes:
   *   httpAuth1 (http)
   *   httpAuth (http)
   *   apiKeyAuth (apiKey)
   */
  def blocking()  =
    basicRequest
      .method(Method.GET, uri"$baseUrl/blocking")
      .contentType("application/json")
      .response(asJson[JsonValueBoolean])

}
