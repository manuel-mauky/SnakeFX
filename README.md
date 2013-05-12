# SnakeFX
This is a clone of the old mobile phone game "Snake".
It was created as an example project to learn *JavaFX 2*.

The object of the game is simple: You are controlling a snake that likes to eat
food. Every time the snake eats some food it grows a little bit. You may not bite yourself in the tail or you are game over. There are no walls in this game so when you move outside of one side of the screen you appear again on the other side of the screen.

### Build the game
The project is based on the build system [maven](http://maven.apache.org/).

This project uses the [maven-javafx-plugin](https://github.com/zonski/javafx-maven-plugin). 

To build an executable jar file do the following steps:

1. JavaFX 2.2 has to be on the systems classpath. To do this type: 
`mvn com.zenjava:javafx-maven-plugin:1.5:fix-classpath`
See [here](https://github.com/zonski/javafx-maven-plugin/wiki/Fixing-the-JRE-classpath) for more informations.

2. Type `mvn` to compile the application and build the executable jar file. The file is located in the `target` directory and has the name `SnakeFX.jar`

Start the game with `java -jar SnakeFX.jar`.

