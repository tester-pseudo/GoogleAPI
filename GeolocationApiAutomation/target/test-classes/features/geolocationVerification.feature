Feature: Geolocation API

  Scenario: Valid Request with All Fields
    Given I have valid geolocation parameters
    When a valid request is sent with "homeMobileCountryCode", "homeMobileNetworkCode", "radioType", "carrier", "considerIp"
    Then the API should return a 200 status code
    And the response should contain accurate geolocation data

  Scenario: Valid Request with IP Consideration Only
    Given I have valid geolocation parameters
    When a valid request is sent with only considerIp: true
    Then the API should return a 200 status code
    And the response should contain accurate geolocation data

  Scenario: Invalid API Key
    Given I have valid geolocation parameters
    When a request is sent with an invalid API key
    Then the API should return a 400 status code
    And the response should indicate an "API key not valid. Please pass a valid API key." error

  Scenario: Missing API Key
    Given I have valid geolocation parameters
    When a request is sent without an API key
    Then the API should return a 403 status code
    And the response should indicate an "Please use API Key or other form of API consumer identity to call this API." error

  Scenario: Invalid homeMobileCountryCode
    Given I have valid geolocation parameters
    When a request is sent with an invalid homeMobileCountryCode
    Then the API should return a 400 status code
    And the response should indicate an "Invalid value at 'home_mobile_country_code'" error

  Scenario: Invalid homeMobileNetworkCode
    Given I have valid geolocation parameters
    When a request is sent with an invalid homeMobileNetworkCode
    Then the API should return a 400 status code
    And the response should indicate an "Invalid value at 'home_mobile_network_code'" error

  Scenario: Unsupported radioType
    Given I have valid geolocation parameters
    When a request is sent with an unsupported radioType
    Then the API should return a 400 status code
    And the response should indicate an "Invalid value at 'radio_type'" error

  Scenario: Invalid JSON Structure
    Given I have valid geolocation parameters
    When a request is sent with malformed JSON
    Then the API should return a 400 status code
    And the response should indicate a "Invalid JSON payload received" error

  Scenario: Consider IP as False
    Given I have valid geolocation parameters
    When a request is sent with Consider IP as false
    Then the API should return a 404 status code
    And the response should indicate a "Not Found" error

  Scenario: Validate with Only homeMobileCountryCode and homeMobileNetworkCode
    Given I have valid geolocation parameters
    When a valid request is sent with only "homeMobileCountryCode" and "homeMobileNetworkCode"
    Then the API should return a 200 status code
    And the response should contain geolocation data based on available information

  Scenario: Valid Request without considerIp
    Given I have valid geolocation parameters
    When a valid request is sent without the considerIp field
    Then the API should return a 200 status code
    And the response should contain accurate geolocation data
