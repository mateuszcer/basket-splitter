# Basket splitter library

### Using the Basket splitter library

#### Prerequisites
* Maven 3.8+
* Java 17+

1. Clone the Repository
   ```bash
   git clone https://github.com/mateuszcer/basket-splitter.git
2. Navigate to the Project Directory
   ```bash
   cd basket-splitter
3. Build the Project
    ```bash
    mvn clean package
4. Include JAR in Your Project
    ```bash
    target/basket-1.0-jar-with-dependencies.jar

#### Usage

1. **Initialize BasketSplitter with JSON config file**
   ```java
   BasketSplitter basketSplitter = new BasketSplitter("path/config.json");
   ```
    ```json
   // example config.json
    {
    "Cookies Oatmeal Raisin": ["Pick-up point"],
    "Cheese Cloth": ["Courier", "Parcel locker", "Same day delivery", "Next day shipping"],
    "English Muffin": ["Mailbox delivery", "Courier", "In-store pick-up", "Parcel locker", "Next day shipping", "Express Collection"],
    "Ecolab - Medallion": ["Mailbox delivery", "Parcel locker", "Courier", "In-store pick-up"],
    "Chocolate - Unsweetened": ["In-store pick-up", "Parcel locker", "Next day shipping"],
    "Sole - Dover, Whole, Fresh": ["In-store pick-up", "Pick-up point"]
    }

2. **Split given basket**
    ```java
    basketSplitter.split(Arrays.asList("Cookies Oatmeal Raisin", "Cheese Cloth", "English Muffin", "Ecolab - Medallion", "Chocolate - Unsweetened", "Sole - Dover, Whole, Fresh"))
    /*
    {
        Pick-up point=[Cookies Oatmeal Raisin, Sole - Dover, Whole, Fresh],
        Parcel locker=[Cheese Cloth, English Muffin, Ecolab - Medallion, Chocolate - Unsweetened]
    }
    */
   