Feature: upload

  Scenario: User want to Upload single airport data file in application
    Given the data type is "Airport" and the local path of the data file is "../seng202_project/src/test/java/TestFiles/singleairport.csv"
    When upload the file via upload page
    Then the data uploaded should contains following information of the airport 14,"Husavik","Husavik","Iceland","HZK","BIHU",65.952328,-17.425978,48,0,"N","Atlantic/Reykjavik"

  Scenario: User want to Upload single route data file in application
    Given the data type is "Route" and the local path of the data file is "../seng202_project/src/test/java/TestFiles/singleRoute.csv"
    When upload the file via upload page
    Then the data uploaded should contains following information of the route "2B",410,"EGO",6156,"KZN",2990,"",0,"CR2"

  Scenario: User want to Upload single route data file in application
    Given the data type is "Airline" and the local path of the data file is "../seng202_project/src/test/java/TestFiles/singleairline.csv"
    When upload the file via upload page
    Then the data uploaded should contains following information of the airline 6,"223 Flight Unit State Airline","\N","","CHD","CHKALOVSK-AVIA","Russia","N"
