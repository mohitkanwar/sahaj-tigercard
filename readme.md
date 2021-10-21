# TigerCard

This project is being developed as Fare Calculation Engine for TigerCard.

## Technologies

* Java 17- Since it is the latest LTS version at the time of development.
* Maven 3.8.3 - Since it is the latest and recommended version at the time of development
* Junit 5.8.1 - Since it is the latest version available at the time of development

## Methodology

Test driven development, since it is more agile and helps keep the code cleaner and more organized.

## Requirements

1. The system should accept a list of journeys and return the fair applicable
    1. The journey would contain the following fields
        1. date-time
        2. from-zone
        3. to-zone
    2. The zone would contain the following fields
        1. name
        2. normalized distance from center
2. Zones are concentric rings, with Zone 1 being at the center of the city, Zone 2 is the outer ring for Zone 1
3. Peak hour calculation will be based on Day of the week and hour slots as mentioned below
   1. Monday to Friday (Both included)
      1. 07:00 - 10:30 (Both included)
      2. 17:00 - 20:00 (Both included)
   2. Saturday - Sunday (Both included)
      1. 09:00 - 11:00 (Both included)
      2. 18:00 - 22:00 (Both included)
4. Any timings not included above are considered as non-peak hours
   1. The fairs will be calculated as below
      1. Zone 1 -> Zone 1  | Peak Hours     | 30
      2. Zone 1 -> Zone 1  | Non-Peak Hours | 25
      3. Zone 1 -> Zone 2  | Peak Hours     | 35
      4. Zone 1 -> Zone 2  | Non- Peak Hours| 30  
      5. Zone 2 -> Zone 1  | Peak Hours     | 35
      6. Zone 2 -> Zone 1  | Non- Peak Hours| 30
      7. Zone 2 -> Zone 2  | Peak Hours     | 25
      8. Zone 2 -> Zone 2  | Non- Peak Hours| 20
      9. Fair Capping : There will be two types of fair capping applicable
         1. Types of capping
            1. Daily fair cap
            2. Weekly fair cap
         2. The applicable capping will be applied from the farthest journey
         3. Below are the capping limits
   
          |  Farthest Zones |  Daily Cap |  Weekly Cap   |
         |---|---|---|
         |  Zone 1 <-> Zone 1   | 100  |  500 |
         |  Zone 1 <-> Zone 2 | 120  | 600  |
         |  Zone 2 <-> Zone 2 | 80  | 400  |