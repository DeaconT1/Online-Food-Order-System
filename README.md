# *QuickBite* Online Food Ordering and Deliverying System

### The core functianality of *QuickBite*
- Allow the users to browse a restaurant's menu.
- View, add or remove the food items with descriptions(details about this food item and comments from other costumers).
- Place an order and check out
- And select food items with description and images.
- Recommend food items based on the user’s ordering habits, providing a more personalized experience.
- The delivery information.

***We warmly welcome food lovers from all over the world to enjoy a diverse selection of convenient and delicious cuisine😋.***

### My motiveation
It addresses a real-world problem that many people, including myself, encounter on a daily basis. In today’s fast-paced world, ordering food online has become an essential service for both students and working professionals. I not only aim to improve the user experience of selecting and ordering food but also gain practical skills in software development. For instance how to manage the data between the users and food item to offer a personalize recommandation, and to keep track on the order and delivery information on admin panel. Additionally, user interface design is really a fun part for me, the better interaction between users and the app will enhance their engagement and interest.

### Users Story:
- As a user, I want to be able to **browse** the menu and see the food descriptions.
- As a user, I want to be able to **add** the food to my order.
- As a user, I want to be able to **view** the food from the order.
- As a user, I want to be able to **remove** the food from the order.
- As a user, I want to be able to **place** the order.
- As a user, I want to be able to **check** the delivery status.
- As a user, I want to be able to **add comment** to a specify food item.
#### For Phase2:
- As a user, I want to be able to **save my order** to file(If I so chosse).
- As a user, I want to be able to **load my previous order** from the file when I reopen the App.
- As a user, I want to be able to **be reminded** to save the order when I press quit(if the order is not empty).


### `X` and `Y` in this desktop application:
- `X` is the `FoodItem`, including its name, description, price, image and comments.
- `Y` is the `Order`, have the list of `FoodItem`, we can add, view, remove, calculate the total price of current food items in it.


## Phase4: Task3 (refactoring):
- The QuickBiteApp is handling too many responsibilities, GUI, all adding and remove event. Also I have instantiated at least three panels (menu, order, welcome-page) here. 
- **Refactoring**: I should extract panels into separate classes.
- After learning the **Observer Pattern** in lecture, I found the possible to use it in my project! When a `FoodItem` is added to the `Order`, obeserve this change and updating the UI rather than using direct method calls. 
- **Refactorying**: First, create an `Observer` interface, then modify the `Order` Class by adding and removing Obeserver methods, also `notifyObservers`. After that, let the `QuickBiteApp` implement the `Observer`, add method like `updateOrderList()` and `updateTotal()` (this is for updating the price and status of the order) for instance. After all, add `notifyObservers()` after the `addFoodItem` and `removeFoodItem`, the Obeserver Pattern realized. It reduce the duplication of method calls and make the behaviour of changing order more clearly.