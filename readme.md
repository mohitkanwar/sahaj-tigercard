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

       | Start Zone | End Zone | Peak hours | Non Peak hours |  
              |------------|----------|------------|----------------|
       | Z1         | Z1       | 30         | 25             |
       | Z1         | Z2       | 35         | 30             |
       | Z2         | Z1       | 35         | 30             |
       | Z2         | Z2       | 25         | 20             |
5. Fair Capping : There will be two types of fair capping applicable
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

## Assumptions

1. The mentioned timings for peak hours include both start and end times
2. The time for calculation is taken to nearest second
3. Start of the week is taken as the day of first journey, (and not a fixed day e.g. sunday or monday)
4. Weeks and Day's calculation is done on clock day (00:00-24:00) irrespective of start time of the first journey
5. Server LocalDate represents

## Possible improvements

1. The data should not be hardcoded
    1. This includes tests as well, in production system, the service should fetch the data from external source and the
       tests should mock the database
2. The code could have been more flexible
3. Capping limit is done based upon highest applicable cap, instead of farthest journey. e.g. a farthest journey could
   be between Z2 and Z2 (due to concentricity of zones), but because of highest fairs being between zone changes, z1-z2
   cap will be applied

## Test cases

1. Single Journey should return correct fair
2. Multiple journeys in a Day without being capped should return correct fair
3. Multiple journeys in a day on capped should return capped fair
4. Weekly journeys without being capped should return correct results
5. Weekly journeys after being capped should return correct results