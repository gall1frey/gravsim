# Gravity Simulator Game
Project for the course **Object Oriented Analysis and Design with Java** that lets you explore orbital dynamics in form of a cool game!


```



```

## User Instructions
### Opening Screen
Here, the user has the option to choose between the following options:
```
1 → Play Game
2 → View Scoreboard
3 → Exit
```
*NOTE: Insert screenshots when done*
#### Play Game
* There are two choices here:
  * **Creative Mode -**
    * Here, the user gets to place their own planets across the canvas and watch their movement. There is no 'gameplay' involved, but it's just as fun!
  * **Play Mode -**
    * Here, the user is prompted to select their level and enter a name. This name will be saved to the scoreboard.
    * Once this is done, the gameplay begins, and the goal is to navigate the rocket from its current position to the goal planet!

#### View Scoreboard
* Select this option to view the scoreboard.
* The scoreboard displays the **top 10** player names and their scores, ranked by score.
* This screen has a button that lets you return to the main screen.

#### Exit
* Quits the game window.

### Scoreboard
*Describe scoreboard screen*

### Gameplay
#### Creative Mode
Here, you can load in a `CSV File` with the following format:
```csv
PLANET_NAME | PLANET_MASS | PLANET_POSITION_X (IN AU) | PLANET_POSITION_X (IN AU) | PLANET_VEL_X (m/s) | PLANET_VEL_Y (m/s) | PLANET_RADIUS
```
And watch your configuration of a solar system come to life!

#### Play Mode
* You get to control a rocket. Different rockets have different masses and max accelerations. They also burn fuel at different rates.
* Your goal is to navigate the rocket to the goal planet (The green one)
* But here's the catch: You can only **accelerate or decelerate**.
* Exploit the gravitational fields of the surrounding planets to your advantage.
* Try to do so before the fuel runs out!

***Allons-y!***

## Developer Instructions
* So far, things are working and I'm afraid to break them xD.
* Also, we using swing + awt here.
* Gonna need a database. Might I suggest cloud mongodb?

### Resoures:
* Follow [this](https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq) playlist for graphics
* Planet motion help: [link](https://www.youtube.com/watch?v=WTLPmUHTPqo). This is python, but the physics is about right

### Math Stuff
* All distance is taken in **AUs (Astronomical Unit)**, unless its when calculating force/vel/etc.
* Unit of velocity across the project is **m/s**.
* Unit of time across the project is **second**.
* Unit of angle across the project is **radians**.

### Todo
1. Draw planet and rocket trails
2. Create goalPlanet variable in Level (last entry of entities[])
3. ~Check for collisions~
4. ~Check if goal reached~
5. Figure out a system of points
6. Print text on screen
7. ~Scale up planet sizes with zoom~
8. Implement scoreboard
9. Implement menu screen
10. Create new levels

### Class Design
#### Models
This package contains all models (as in, the MVC architecture)
##### Entity
Is an abstract class that describes an entity in space. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
Mass      (double)
Velocity  (double[])
Position  (double[])
Radius    (double)
Name      (String)
trail     (Trail)
-------------------------------------
Methods
-------------------------------------
getters and setters
for vel and pos
-------------------------------------
```
##### Planet
Describes a planet. Derives from `Entity`. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
planet_sprite (Image)
-------------------------------------
Methods
-------------------------------------
getter and setter for planet_sprite
Constructor
-------------------------------------
```
##### Rocket
Describes a rocket. Derives from `Entity`. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
fuelPercentage            (float)
rocketSprite              (Image)
rocketSpriteAccelerating  (Image[])
rocketSpriteDecelerating  (Image[])
fuelBurnedPerTime         (float)
forcePerFuelBurnt         (float)
-------------------------------------
Methods
-------------------------------------
getter and setter for rocket_sprite
getter and setter for fuelPercentage
Constructor
accelerateTo
-------------------------------------
```

##### Trail
Describes a trail that'll follow the entity. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
pathTravelled     (LinkedList<double[]>
lenOfTrail        (long double)
-------------------------------------
Methods
-------------------------------------
getter and setter for lenOfTrail
getter and setter for fuelPercentage
addPathTravelled
getPathTravelled
-------------------------------------
```

##### Physics
Describes the physics that exists in the world. Is a singleton class. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
univGravConst     (double)
astronomicalUnit  (double)
timestep          (double)
scaleFactor       (double)
scale             (double)
-------------------------------------
Methods
-------------------------------------
newVel
newPos
gravForce
getDistance
getAngle
getComponents
updateScale
getScale
auToM
mToAu
-------------------------------------
```
##### Level
Describes a level. Could be used as a model to store level in db? Has the following:
```
-------------------------------------
Attributes
-------------------------------------
levelName     (String)
entities      (Entity[])
rocketMove    (boolean)
planetsMove   (boolean)
physics       (Physics)
-------------------------------------
Methods
-------------------------------------
Constructor
getters and setters
handleAcceleration
handleDeceleration
update
-------------------------------------
```
##### Player
Player model to store in db. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
playerName        (String)
playerPoints      (int)
playerHighScore   (int)
levelName         (String)
-------------------------------------
Methods
-------------------------------------
Constructor
getters and setters
-------------------------------------
```
#### Views
#### gameplayScreen
Handles all views of the gameplay (both modes). Has the following:
```
-------------------------------------
Attributes
-------------------------------------
offsetX         (int)
offsetY         (int)
fuelBarLen      (int)
fuelBarx        (int)
fuelBary        (int)
fuelBarHeight   (int)
background      (Image)
playFrame       (Image)
st_instance     (screenTranslator)
physics         (Physics)
-------------------------------------
Methods
-------------------------------------
Constructor
updateScale
renderFuelBar
renderBackground
renderEntities
renderPlayFrame
renderPlayerName
rotate
getDefaultConfiguration
getters and setters of offsets
-------------------------------------
```
##### inputView
Handles views for input screen. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
background  (Image)
frame       (Image)
-------------------------------------
Methods
-------------------------------------
Constructor
getPlayChoice
getPlayMode
getPlayerName
-------------------------------------
```
##### scoreboardView
Handles views for scoreboard. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
background  (Image)
frame       (Image)
-------------------------------------
Methods
-------------------------------------
Constructor
displayScoreboard
-------------------------------------
```
##### screenTranslator
Converts position to pixels. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
translator_instance   (screenTranslator)
physics               (Physics)
-------------------------------------
Methods
-------------------------------------
Constructor
convertPosToPixel
-------------------------------------
```
#### Controllers
##### KeyHandler
Handles keypresses. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
upPressed     (boolean)
downPressed   (boolean)
wPressed      (boolean)
aPressed      (boolean)
sPressed      (boolean)
dPressed      (boolean)
zInPressed    (boolean)
zOutPressed   (boolean)
-------------------------------------
Methods
-------------------------------------
keyTyped
keyPressed
keyReleased
-------------------------------------
```
#### businessLogic
##### EntityCatalogue and LevelCatalogue
Contains list of viable entities and levels.

Ideally, get rid of this class / push to database
##### GamePanel
GamePlay done here. Has the following:
```
-------------------------------------
Attributes
-------------------------------------
screenWidth       (int)
screenHeight      (int)
keyH              (keyHandler)
gameThread        (Thread)
player            (Player)
level             (Level)
screen            (gameplayScreen)
FPS               (int)
status            (int)
-------------------------------------
Methods
-------------------------------------
Constructor
startGameThread
run
update
paintComponent
Collision
-------------------------------------
```
#### Main
##### MainApp
The main app. **Check this out first**.
