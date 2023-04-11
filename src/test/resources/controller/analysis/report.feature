Feature: generating the carbon emission report

  Scenario: Report displays the correct total carbon emissions produced from the user's flight history.
    Given The user has uploaded route data and airport data and added a route "2B",410,"AER",2965,"SFG",2990,"",0,"CR2" and set the number of passengers to 1 into the flight history and is currently on the carbon emissions report page and the user has entered their goal as 1000 kg
    When The user clicks the generate report button
    Then The total carbon emissions of 874.99 kg is displayed.


